package com.oakay.contracts.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalContext
import com.oakay.contracts.data.Order
import com.oakay.contracts.data.OrderRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MainApp() {
    val repo = remember { OrderRepository.getInstance(LocalContext.current) }
    val orders by repo.getAllFlow().collectAsState(initial = emptyList())
    val scaffoldState = rememberScaffoldState()
    var showAdd by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Oakay Contracts") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAdd = true }) {
                Text("+") 
            }
        },
        scaffoldState = scaffoldState
    ) { inner ->
        Box(modifier = Modifier.fillMaxSize().padding(inner)) {
            if (!showAdd) {
                OrdersList(orders = orders, onToggleComplete = { id ->
                    // toggle status
                    val o = orders.find { it.id == id } ?: return@OrdersList
                    val updated = o.copy(status = if (o.status == "COMPLETED") "IN_PROGRESS" else "COMPLETED")
                    repo.updateBlocking(updated)
                })
            } else {
                AddOrderScreen(onSave = { order ->
                    repo.insertBlocking(order)
                    showAdd = false
                }, onCancel = { showAdd = false })
            }
        }
    }
}

@Composable
fun OrdersList(orders: List<Order>, onToggleComplete: (Long)->Unit) {
    LazyColumn {
        items(orders.size) { idx ->
            val o = orders[idx]
            OrderCard(o, onToggleComplete)
        }
    }
}

@Composable
fun OrderCard(order: Order, onToggleComplete: (Long)->Unit) {
    val due = Date(order.dueDate)
    val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val bg = when {
        order.status == "COMPLETED" -> Color(0xFFDFF6DF)
        order.dueDate < System.currentTimeMillis() && order.status != "COMPLETED" -> Color(0xFFFFEDEE)
        else -> Color(0xFFFFF9E6)
    }
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .background(bg)) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(order.clientName, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(order.orderName)
                Text("Сумма: ${order.amount}  Предоплата: ${order.prepayment}")
                Text("Срок сдачи: ${sdf.format(due)}")
            }
            Checkbox(checked = order.status == "COMPLETED", onCheckedChange = { onToggleComplete(order.id) })
        }
    }
}

@Composable
fun AddOrderScreen(onSave: (Order)->Unit, onCancel: ()->Unit) {
    var name by remember { mutableStateOf("") }
    var orderName by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var prepay by remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Ф.И.О.") })
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = orderName, onValueChange = { orderName = it }, label = { Text("Наименование заказа") })
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = amount, onValueChange = { amount = it }, label = { Text("Сумма") })
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = prepay, onValueChange = { prepay = it }, label = { Text("Предоплата") })
        Spacer(Modifier.height(16.dp))
        Row {
            Button(onClick = {
                val now = System.currentTimeMillis()
                val oneMonth = 30L * 24 * 60 * 60 * 1000
                val due = now + oneMonth
                val o = Order(
                    id = 0L,
                    clientName = name,
                    orderName = orderName,
                    amount = amount.toDoubleOrNull() ?: 0.0,
                    prepayment = prepay.toDoubleOrNull() ?: 0.0,
                    startDate = now,
                    dueDate = due,
                    status = "IN_PROGRESS",
                    mediaJson = null
                )
                onSave(o)
            }) { Text("Сохранить") }
            Spacer(Modifier.width(8.dp))
            OutlinedButton(onClick = onCancel) { Text("Отмена") }
        }
    }
}
