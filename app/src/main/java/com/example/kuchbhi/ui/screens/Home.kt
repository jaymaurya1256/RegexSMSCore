package com.example.kuchbhi.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.kuchbhi.utils.BASE_URL
import com.example.kuchbhi.utils.PreferenceHelper
import com.example.kuchbhi.viewModels.HomeViewModel


private const val TAG = "Home"

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        PreferenceHelper(context).getStringList("clients").toMutableStateList().forEach {
            viewModel.clients.add(it)
        }
        viewModel.regex = PreferenceHelper(context).getString("regex", "").toString()
        viewModel.baseUrl = PreferenceHelper(context).getString("baseUrl", "").toString()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LazyRow(modifier = Modifier.padding(vertical = 8.dp)) {
            items(viewModel.clients) { name ->
                AssistChip(
                    onClick = { },
                    label = { Text(name) },
                    leadingIcon = {
                        Icon(Icons.Filled.Person, contentDescription = null)
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            viewModel.clients.remove(name)
                        }) {
                            Icon(Icons.Filled.Close, contentDescription = "Remove")
                        }
                    },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        OutlinedTextField(
            value = viewModel.from,
            onValueChange = { viewModel.from = it },
            label = { Text("From") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = {
                    viewModel.clients.add(viewModel.from)
                    viewModel.from = ""
                }) {
                    Icon(Icons.Filled.Add, contentDescription = "Add")
                }
            },
            isError = viewModel.errorMessage.isNotBlank()
        )

        Spacer(modifier = Modifier.height(16.dp))


        OutlinedTextField(
            value = viewModel.regex,
            onValueChange = { viewModel.regex = it },
            label = { Text("Message Pattern") },
            modifier = Modifier.fillMaxWidth(),
            isError = viewModel.errorMessage.isNotBlank()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.baseUrl,
            onValueChange = { viewModel.baseUrl = it },
            label = { Text("Api(BaseUrl + Endpoint)") },
            modifier = Modifier.fillMaxWidth(),
            isError = viewModel.errorMessage.isNotBlank()
        )

        Spacer(modifier = Modifier.height(16.dp))


        Row {
            Text(
                text = if (viewModel.mode == "or") "Match any field" else "Match all fields",
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            Switch(
                checked = viewModel.mode != "or",
                onCheckedChange = { viewModel.mode = if (viewModel.mode == "or") "and" else "or" },
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (((viewModel.clients.isNotEmpty() || viewModel.regex.isNotBlank() && viewModel.mode == "or") ||
                            (viewModel.clients.isNotEmpty() && viewModel.regex.isNotBlank() && viewModel.mode == "and")) &&
                    viewModel.baseUrl.isNotEmpty()
                ) {
                    if (viewModel.clients.isNotEmpty()) {
                        PreferenceHelper(context).saveStringList("clients", viewModel.clients)
                    }
                    if (viewModel.regex.isNotEmpty()) {
                        PreferenceHelper(context).saveString("regex", viewModel.regex)
                    }
                    if (viewModel.baseUrl.isNotEmpty()) {
                        PreferenceHelper(context).saveString("baseUrl", viewModel.baseUrl)
                    }
                    PreferenceHelper(context).saveString("mode", viewModel.mode)
                    Toast.makeText(
                        context,
                        "Your preferences has been saved",
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.from = ""
                    viewModel.regex = ""
                    viewModel.baseUrl = ""
                    Toast.makeText(context, "Configurations saved!", Toast.LENGTH_SHORT).show()
                }
                viewModel.validateInput()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit", fontSize = 24.sp)
        }
    }
}
