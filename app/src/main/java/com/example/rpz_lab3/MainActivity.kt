package com.example.rpz_lab3

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rpz_lab3.ui.theme.RPZ_lab3Theme
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RPZ_lab3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BasicCalculator()
                }
            }
        }
    }
}

@Composable
fun BasicCalculator(){
    val navController = rememberNavController()
    var num1 by remember { mutableStateOf("") }
    var num2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    val radioOptions = listOf("+", "-", "x", "/")
    val selectedOperation = remember { mutableStateOf(radioOptions[0]) }
    val context = LocalContext.current
    val filename = "user_results.txt"
    val file = File(context.filesDir, filename)

    NavHost(navController = navController, startDestination = "default") {
        composable("default"){
            DefaultFragment(
                num1 = num1,
                onNum1Change = { value ->
                    if (value.all { char -> char.isDigit() || char == '.' }){
                        num1 = value
                    }
                },
                num2 = num2,
                onNum2Change = { value ->
                    if (value.all { char -> char.isDigit() || char == '.' }){
                        num2 = value
                    }
                },
                selectedOperation = selectedOperation.value,
                onOperationSelected = { selectedOperation.value = it },
                onComputeClick = {
                    val number1 = num1.toDoubleOrNull()
                    val number2 = num2.toDoubleOrNull()

                    result = when {
                        number1 == null || number2 == null -> "Неправильний ввід даних"
                        selectedOperation.value == "/" && number2 == 0.0 -> "Ділення на 0 неможливе"
                        else -> {
                            val computedResult = when (selectedOperation.value){
                                "+" -> (number1 + number2)
                                "-" -> (number1 - number2)
                                "x" -> (number1 * number2)
                                "/" -> (number1 / number2)
                                else -> "Сталася невідома помилка"
                            }
                            "Результат: $computedResult"
                        }
                    }

                    if (result.startsWith("Результат:") ) {
                        file.appendText("$number1 ${selectedOperation.value} $number2 = " +
                                "${result.substring(11)}\n")
                    } else {
                        file.appendText("${number1.toString()} ${selectedOperation.value} " +
                                "${number2.toString()} = $result\n")
                    }


                    navController.navigate("result")
                },
                onSeeAllResultsClick = {
                    val intent = Intent(context, ShowResultActivity::class.java)
                    context.startActivity(intent)
                },
                onDeleteResultsClick = {
                    file.writeText("")
                }
            )
        }

        composable("result"){
            ResultFragment(
                result = result,
                onCancelClick = {
                    num1 = ""
                    num2 = ""
                    selectedOperation.value = "+"
                    navController.navigate("default"){
                        popUpTo("default"){ inclusive = true }
                    }
                }
            )
        }
    }
}