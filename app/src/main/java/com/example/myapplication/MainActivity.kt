package com.example.myapplication


import android.os.Bundle
//import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
//import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*;
import androidx.compose.ui.draw.clip
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.ui.graphics.Color
import com.example.myapplication.ui.theme.Purple500
import com.example.myapplication.ui.theme.gray1


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme{
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }
    }
}
@Composable
fun MainScreen() {
    val checkAmount: MutableState<String> = remember { mutableStateOf("") }
    val tipPercentage: MutableState<Float?> = remember { mutableStateOf(null) }
    val result: MutableState<String> = remember { mutableStateOf("") }
    val title = remember {
        mutableStateOf("Tip Calculator")
    }
    val subtitle = remember {
        mutableStateOf("Enter your check amount:")
    }
    val rand = remember {
        mutableStateOf("Enter your tip percentage")
    }
    var showError = remember { mutableStateOf(false) }

    val customTextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        backgroundColor = Color.LightGray
    )
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title.value,//stringResource(id = R.string.titlec2f),
            fontSize = 28.sp,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = subtitle.value,//stringResource(id = R.string.titlec2f),
            fontSize = 16.sp,
            modifier = Modifier.padding(16.dp)
        )
        Row(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Text(
                text = "$",//stringResource(id = R.string.titlec2f),
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
                OutlinedTextField(
                    value = checkAmount.value,
                    onValueChange = { newValue: String ->
                        checkAmount.value = newValue
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default
                        .copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            calculateTip(checkAmount.value, tipPercentage.value)?.let {
                                result.value = it
                            }
                        }
                    ),

                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)
                )

        }
        Row(
            modifier = Modifier.padding(1.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(48.dp)
                    .padding(4.dp)

                    .background(
                        if (tipPercentage.value == 0.1f) Purple500 else Color.LightGray
                    )
                    .border(
                        width = 1.dp,
                        color = gray1,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(MaterialTheme.shapes.small)
                    .clickable { tipPercentage.value = 0.1f },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "10%",
                    fontSize = 16.sp,
                    color = if (tipPercentage.value == 0.1f) Color.Black else Color.Black,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )
            }
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(48.dp)
                    .padding(4.dp)

                    .background(
                        if (tipPercentage.value == 0.2f) Purple500 else Color.LightGray
                    )
                    .border(
                        width = 1.dp,
                        color = gray1,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(MaterialTheme.shapes.small)
                    .clickable { tipPercentage.value = 0.2f },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "20%",
                    fontSize = 16.sp,
                    color = if (tipPercentage.value == 0.2f) Color.Black else Color.Black,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )
            }
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(48.dp)
                    .padding(4.dp)
                    .background(
                        if (tipPercentage.value == 0.3f) Purple500 else Color.LightGray
                    )
                    .border(
                        width = 1.dp,
                        color = gray1,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(MaterialTheme.shapes.small)
                    .clickable { tipPercentage.value = 0.3f },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "30%",
                    fontSize = 16.sp,
                    color = if (tipPercentage.value == 0.3f) Color.Black else Color.Black,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )
            }
        }
        Button(
            onClick = {
                showError.value = true
                calculateTip(checkAmount.value, tipPercentage.value)?.let {
                    result.value = it
                }
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Calculate")
        }
        Text(
            text = if((result.value == "ERROR" || checkAmount.value.isEmpty()) && showError.value) "Tip: ERROR" else "Tip: ${result.value}",
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = if((result.value == "ERROR" || checkAmount.value.isEmpty() && showError.value)) "Total: ERROR" else "Total: ${calculateTotal(checkAmount.value, tipPercentage.value)}",
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
        }
    }

fun calculateTip(checkAmount: String, tipPercentage: Float?): String? {
    val amount = checkAmount.toDoubleOrNull() ?: return null
    if (tipPercentage == null) {
        return "ERROR"
    }
    val tipAmount = amount * tipPercentage
    return "\$${"%.2f".format(tipAmount)}"
}

fun calculateTotal(checkAmount: String, tipPercentage: Float?): String? {
    val amount = checkAmount.toDoubleOrNull() ?: return null
    if (tipPercentage == null) {
        return "ERROR"
    }
    val totalAmount = amount * (1 + tipPercentage)
    return "\$${"%.2f".format(totalAmount)}"
}


        @Preview(showBackground = true)
        @Composable
        fun DefaultPreview() {
            MyApplicationTheme() {
                MainScreen()
            }
        }

// TODO - 12. (Optional) Dismiss the keyboard and update the result when "Enter/Go" is pressed
// TODO - 13. (Optional) Update the title when the radio buttons change


//all below from Temperature conversion, lab 5:
//OutlinedTextField(
//value = originalTemp.value,
//onValueChange = {
//    newValue: String ->
//    originalTemp.value = newValue
//},
//placeholder = {
//    Text(stringResource(R.string.placeholder))
//},
//singleLine = true,
//keyboardOptions = KeyboardOptions
//.Default
//.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Go),
//modifier = Modifier.padding(16.dp)
//)
//Row(
//modifier = Modifier.padding(16.dp)
//) {
//    RadioButton(
//        selected = convertType.value == ConvertType.F2C,
//        onClick = {
//            convertType.value =ConvertType.F2C
//            title.value = "Convert F to C"
//        }
//    )
//    Text(stringResource(id = R.string.f2c_label))
//}
//Row {
//    RadioButton(
//        selected = convertType.value == ConvertType.C2F,
//        onClick = {
//            convertType.value = ConvertType.C2F
//            title.value ="convert C to F"
//        }
//    )
//    Text(stringResource(id = R.string.c2f_label))
//}
//Button(
//onClick = {
//    try {
//        val resultTemp =  convertTemp(originalTemp.value, convertType.value)
//        result.value = resultTemp.toString()
//    } catch (e: java.lang.NumberFormatException){
//        Log.e(MainActivity::class.java.simpleName, "Error converting temp", e)
//        result.value = "ERROR"
//    }
//},
//modifier = Modifier.padding(16.dp)
//) {
//    Text(stringResource(R.string.convert_btn))
//}
//Text(stringResource(R.string.result_label))
//Text(
//text = "Result: ${result.value}",
//fontSize = 28.sp
//)
//}
//}
//fun convertTemp(originalTemp: String, convertType: ConvertType): Double {
//    val temp: Double = originalTemp.toDouble()
//    return when(convertType){
//        ConvertType.F2C -> {
//            (temp - 32.0) * (5.0/9.0)
//        }
//        ConvertType.C2F -> {
//            (temp * (9.0/5.0)) + 32.0
//        }
//    }
//}
//enum class ConvertType{
//    F2C, C2F
//}