package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusDirection.Companion.In
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.Copy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import java.text.NumberFormat


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TipCalculatorScreen()
                }
            }
        }
    }
}
@Composable
fun TipCalculatorScreen() {
    var serviceCostAmountInput by remember { mutableStateOf("") }
    var tipPercent by remember { mutableStateOf(15.0) }
    val amount = serviceCostAmountInput.toDoubleOrNull() ?: 0.0
    val tip = calculateTip(amount, tipPercent).round(2)
    val total = (amount + tip).round(2)

    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.tip_calculator_heading),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(16.dp))



        Spacer(Modifier.height(16.dp))

        GenerateRandomBillButton(
            serviceCostAmountInput = serviceCostAmountInput,
            onValueChange = { serviceCostAmountInput = it }
        )

        Spacer(Modifier.height(16.dp))

        EditServiceCostField(
            value = serviceCostAmountInput,
            onValueChange = { serviceCostAmountInput = it }
        )

        Spacer(Modifier.height(24.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            PercentageButton(text = "10%", onClick = {tipPercent = 10.0})
            PercentageButton(text = "15%", onClick = {tipPercent = 15.0})
            PercentageButton(text = "18%", onClick = {tipPercent = 18.0})
            PercentageButton(text = "20%", onClick = {tipPercent = 20.0})
        }


        Spacer(Modifier.height(48.dp))

        Text(
            text = stringResource(R.string.bill_amount, "$%.2f".format(amount)),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = stringResource(R.string.tip_amount, "$%.2f".format(tip)),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Line()

        Text(
            text = stringResource(R.string.total_amount, "$%.2f".format(total)),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )


}
}

@Composable
fun PercentageButton(text: String, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text = text)
    }
}

@Composable
fun Line(){
    Divider(
        color = Color.Gray,
        thickness = 3.dp,
        modifier = Modifier.padding(bottom = 6.dp)

    )

}


@Composable
fun GenerateRandomBillButton(
    serviceCostAmountInput: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            val randomBillAmount = (100..200).random()
            onValueChange(randomBillAmount.toString())
        }) {
            Text(stringResource(R.string.generate_random_bill))
        }
    }
}



@Composable
fun EditServiceCostField(
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(R.string.service_cost)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

private fun calculateTip(
    amount: Double,
    tipPercent: Double = 15.0
): Double {
    val tip = tipPercent / 100 * amount
    return tip.round(2)
}

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return kotlin.math.round(this * multiplier) / multiplier
}




@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipCalculatorTheme {
        TipCalculatorScreen()
    }
}