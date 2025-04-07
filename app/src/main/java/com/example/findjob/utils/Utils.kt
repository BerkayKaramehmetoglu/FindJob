package com.example.findjob.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomText(
    text: String,
    modifier: Modifier,
    color: Color,
    fontWeight: FontWeight = FontWeight.Bold,
    fontSize: TextUnit = 20.sp
) {
    Text(
        text = text,
        modifier = modifier,
        textAlign = TextAlign.Center,
        color = color,
        fontWeight = fontWeight,
        fontSize = fontSize
    )
}

@Composable
fun CustomTxtField(
    modifier: Modifier,
    value: MutableState<String>,
    label: String,
    iconResId: Int,
    placeholder: String,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    val visualTransformation: VisualTransformation = if (isPassword) {
        PasswordVisualTransformation()
    } else {
        VisualTransformation.None
    }
    OutlinedTextField(
        value = value.value,
        leadingIcon = {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = null
            )
        },
        onValueChange = { value.value = it },
        label = { Text(text = label, fontSize = 20.sp, fontWeight = FontWeight.Bold) },
        placeholder = { Text(text = placeholder, fontSize = 17.sp, fontWeight = FontWeight.Bold) },
        modifier = modifier,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
    )
}

@Composable
fun CustomElevatedBtn(text: String, modifier: Modifier, onClick: () -> Unit) {
    ElevatedButton(
        onClick = { onClick() }, colors = ButtonDefaults.elevatedButtonColors(
            containerColor = Color(0xFFB34086)
        ),
        contentPadding = PaddingValues(50.dp, 20.dp),
        modifier = modifier
    ) {
        Text(
            text,
            color = Color(0xFFFFE642),
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
        )
    }
}
