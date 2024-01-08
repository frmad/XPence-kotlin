import firebase_admin
from firebase_admin import credentials, messaging, firestore
from firebase_functions import https_fn

# Initialize Firebase Admin SDK
if not firebase_admin._apps:
    cred = credentials.Certificate('.key.json')
    firebase_admin.initialize_app(cred)

@https_fn.on_request()
def on_request_example(req: https_fn.Request) -> https_fn.Response:
    request_data = req.get_json()
    title = request_data.get('title', 'Welcome')
    body = request_data.get('body', 'Thank you for using our app')

    send_notifications(title, body)
    return https_fn.Response("Hello world!")
@https_fn.on_group_request()
def on_group_requst(req: https_fn.Request) -> https_fn.Response:
    request_data = req.get_json()
    title = request_data.get('title', 'Group Update')
    body = request_data.get('body', 'Something happend in your group')
def send_notifications(Title, Body):
    db = firestore.client()
    users_ref = db.collection('users')
    tokens = [user.get('token') for user in users_ref.stream() if user.get('token')]

    if tokens:
        notification = messaging.Notification(
            title=Title,
            body=Body
        )

        message = messaging.MulticastMessage(
            notification=notification,
            tokens=tokens  # Assuming tokens is a list of FCM tokens
        )

        response = messaging.send_multicast(message)
        print('Successfully sent message:', response)
    else:
        print('No tokens found')

    return 'Notifications sent', 200

def send_group_update_notifications(Title,Body,usernames):
    db = firestore.client()
    users_ref = db.collection('auth_users')
    tokens = [user.get('token') for user in users_ref.where('username', 'in', usernames).stream() if user.get('token')]
    if tokens:
        notification = messaging.Notification(
            title='Your Notification Title',
            body='Your Notification Body'
        )

        message = messaging.MulticastMessage(
            notification=notification,
            tokens=tokens  # Assuming tokens is a list of FCM tokens
        )

        response = messaging.send_multicast(message)
        print('Successfully sent message:', response)
    else:
        print('No tokens found')

    return 'Notifications sent', 200
