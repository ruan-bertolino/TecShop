package com.example.tecshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Headset
import androidx.compose.material.icons.filled.Laptop
import androidx.compose.material.icons.filled.Mouse
import androidx.compose.material.icons.filled.Speaker
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tecshop.ui.theme.TecShopTheme
import java.text.NumberFormat
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val produtos = listOf(
            Produto("Fone Bluetooth", 150.0, "Áudio", Icons.Default.Headset),
            Produto("Notebook", 3200.0, "Informática", Icons.Default.Laptop),
            Produto("Mouse", 45.0, "Informática", Icons.Default.Mouse),
            Produto("Caixa de Som", 220.0, "Áudio", Icons.Default.Speaker)
        )

        setContent {
            TecShopTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CatalogoDeProdutos(produtos = produtos)
                }
            }
        }
    }
}

data class Produto(
    val nome: String,
    val preco: Double,
    val categoria: String,
    val icone: ImageVector
)

@Composable
fun ProdutoCard(produto: Produto, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = produto.icone,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = produto.nome, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = produto.categoria, fontSize = 13.sp, color = Color.Gray)
            }
            Text(
                text = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR")).format(produto.preco),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun CatalogoDeProdutos(produtos: List<Produto>, modifier: Modifier = Modifier) {
    var termoBusca by remember { mutableStateOf("") }
    val produtosFiltrados = produtos.filter {
        it.nome.contains(termoBusca, ignoreCase = true)
    }
    val produtosPorCategoria = produtosFiltrados.groupBy { it.categoria }

    Column(modifier = modifier) {
        TextField(
            value = termoBusca,
            onValueChange = { termoBusca = it },
            label = { Text("Buscar produto") },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )
        LazyColumn(contentPadding = PaddingValues(16.dp)) {

            items(produtosFiltrados) { produto ->
                ProdutoCard(produto = produto)
            }
        }
    }
}
