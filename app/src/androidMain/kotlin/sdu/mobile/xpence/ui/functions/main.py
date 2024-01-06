# Welcome to Cloud Functions for Firebase for Python!
# To get started, simply uncomment the below code or create your own.
# Deploy with `firebase deploy`

from firebase_functions import https_fn
from firebase_admin import initialize_app

import firebase_admin
from firebase_admin import credentials, messaging, firestore
initialize_app()


@https_fn.on_request()
def on_request_example(req: https_fn.Request) -> https_fn.Response:
     return https_fn.Response("Hello world!")

if not firebase_admin._apps:
    cred = credentials.Certificate('.key.json')
    firebase_admin.initialize_app(cred)
def send_notifications(request):
    db = firestore.client()
    users_ref = db.collection('users')
    tokens = [user.get('token') for user in users_ref.stream()]

    # Notification content
    message = messaging.Message(
        notification=messaging.Notification(
            title='Your Notification Title',
            body='Your Notification Body'
        ),
        tokens=tokens,
    )

    # Send a message to the devices subscribed to the provided topic.
    response = messaging.send_multicast(message)
    print('Successfully sent message:', response)

    return 'Notifications sent', 200

