package sdu.mobile.xpence.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import sdu.mobile.xpence.ui.components.groupCard.GroupCard
import sdu.mobile.xpence.ui.screens.GroupDetail
import sdu.mobile.xpence.ui.theme.Purple80
import sdu.mobile.xpence.ui.utils.QueryState
import sdu.mobile.xpence.ui.utils.getExpenses
import sdu.mobile.xpence.ui.utils.getGroups
import sdu.mobile.xpence.ui.utils.usingAPI

@Composable
fun GroupList() {
    val navigator = LocalNavigator.currentOrThrow

    val result by usingAPI { client ->
        getGroups(client)
    }
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ){
        when (val res = result) {
            is QueryState.Success -> {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(5.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    res.data.forEach { group ->
                        val expenses by usingAPI { client ->
                            getExpenses(client, group.id)
                        }
                        when(val ex = expenses){
                            is QueryState.Success -> {
                                var amount = 0
                                ex.data.forEach { expense ->
                                    amount += expense.amountInCents
                                }
                                GroupCard(group, amount/100, group.currency_code
                                ) { navigator.parent?.push(GroupDetail(group)) }
                            }
                            is QueryState.Error -> Text(
                                text = ex.message,
                                color = Color.Red)
                            is QueryState.Loading -> Text(
                                text = "Loading",
                                color = Purple80)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
            is QueryState.Error -> Text(
                text = res.message,
                color = Color.Red
            )
            is QueryState.Loading -> Text(
                text = "Loading",
                color = Purple80
            )
        }
    }
}


