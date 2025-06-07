package kien.projects.focai.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kien.projects.focai.ui.theme.FocAITheme

@Composable
fun LoginScreen() {
    Scaffold() { innerPadding ->
        LoginScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}


@Composable
fun LoginScreenContent(modifier: Modifier = Modifier) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        UsernameTextFiled(value = username) {
            username = it
        }
        PasswordTextField(value = password) {
            password = it
        }
    }
}


@Composable
fun UsernameTextFiled(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
) {
    var value by remember { mutableStateOf(value) }

    Column(modifier = modifier) {
        Text("Username")
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = {
                value = it
                onValueChange(it)
            }
        )
    }
}

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
) {
    var value by remember { mutableStateOf(value) }
    Column(modifier = modifier) {
        Text("Password")
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = {
                value = it
                onValueChange(it)
            }
        )
    }
}





@Preview
@Composable
fun LoginScreenPreview() {
    FocAITheme {
        LoginScreen()
    }
}