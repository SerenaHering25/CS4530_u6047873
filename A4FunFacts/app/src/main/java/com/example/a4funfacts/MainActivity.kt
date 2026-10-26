package com.example.a4funfacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a4funfacts.ui.theme.A4FunFactsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Get the one repository from the Application class
        val repo = (application as FactsApp).repository
        setContent {
            A4FunFactsTheme {
                // Build the ViewModel through the Factory
                val vm: FactVM = viewModel(factory = FactVMFactory(repo))
                FactsJPC(vm)
            }
        }
    }
}

@Composable
fun FactsJPC(vm: FactVM) {
    val facts by vm.facts.collectAsState()          // all saved facts
    var query by remember { mutableStateOf("") }    // search box text

    // Show facts containing the search text and all of them if the box is empty
    val shown = facts.filter { it.text.contains(query, ignoreCase = true) }

    Column(
        Modifier.fillMaxWidth().padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { vm.fetchFact() }) {
            Text("Fetch Fact")
        }

        Spacer(Modifier.height(20.dp))

        // Updates search on every keystroke
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search saved facts") },
            singleLine = true
        )

        Spacer(Modifier.height(20.dp))
        Text("FunFact List", fontSize = 25.sp, fontWeight = FontWeight.ExtraBold, color = Color.Blue)

        LazyColumn {
            items(shown) { fact ->
                Text(fact.text, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(10.dp))
            }
        }
    }
}