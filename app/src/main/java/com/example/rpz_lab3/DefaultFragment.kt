package com.example.rpz_lab3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun DefaultFragment(
    num1: String,
    onNum1Change: (String) -> Unit,
    num2: String,
    onNum2Change: (String) -> Unit,
    selectedOperation: String,
    onOperationSelected: (String) -> Unit,
    onComputeClick: () -> Unit,
    onSeeAllResultsClick: () -> Unit,
    onDeleteResultsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp),
        verticalArrangement = Arrangement.Top
    ){
        Text(
            text = "Найпростіший калькулятор",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color(0xffb5b5b5),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        verticalArrangement = Arrangement.Center
    ) {
        //Перше текстове поле вводу даних
        OutlinedTextField(
            value = num1,
            onValueChange = onNum1Change,
            label = { Text("Введіть число") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        )

        //радіо-батони
        val radioOptions = listOf("+", "-", "x", "/")
        Row(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .selectableGroup()){
            radioOptions.forEach { op ->
                Row(
                    Modifier
                        .selectable(
                            selected = (op == selectedOperation),
                            onClick = { onOperationSelected(op) },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 5.dp)

                ) {
                    RadioButton(
                        selected = (op == selectedOperation),
                        onClick = { onOperationSelected(op) },
                        enabled = true,
                        colors = RadioButtonDefaults.colors(
                            Color.Magenta,
                            Color.DarkGray
                        )
                    )
                    Text(
                        text = op,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal = 3.dp, vertical = 15.dp)
                    )
                }
            }
        }

        //друге текстове поле вводу даних
        OutlinedTextField(
            value = num2,
            onValueChange = onNum2Change,
            label = { Text("Введіть число") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        )

        Row (modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(10.dp))
        {
            //кнопка обчислення результату вибраної попередньо операції
            Button(
                onClick = onComputeClick,
                colors = ButtonDefaults.buttonColors(Color(0xff0e8da4)),
                modifier = Modifier
                    .padding(top=10.dp, end=10.dp)
                    .width(70.dp)
            ) { Text("=") }

            //кнопка для переходу до нової діяльності, де будуть відображені
            //всі попередні результати виконання операцій
            Button(
                onClick = onSeeAllResultsClick,
                colors = ButtonDefaults.buttonColors(Color(0xff0e8da4)),
                modifier = Modifier
                    .padding(top=10.dp, start=10.dp)
                    .width(200.dp)

            ) { Text("Минулі результати") }
        }

        Box(modifier = Modifier
            .padding(start=135.dp)
            .size(width=210.dp,height=100.dp)) {
            //кнопка для стирання вмісту файлу, де зберігаються всі результати
            Button(
                onClick = onDeleteResultsClick,
                colors = ButtonDefaults.buttonColors(Color(0xff456789)),
                modifier = Modifier
                    .width(200.dp)
            ) { Text("Стерти результати") }
        }
    }
}