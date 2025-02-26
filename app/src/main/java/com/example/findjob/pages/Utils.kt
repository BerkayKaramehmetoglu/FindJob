package com.example.findjob.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun CustomText(
    text: String,
    color: Color,
    fontWeight: FontWeight = FontWeight.Bold,
    fontSize: TextUnit = 20.sp
) {
    Text(
        text = text,
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = color,
        fontWeight = fontWeight,
        fontSize = fontSize
    )
}

@Composable
fun CustomTxtField(
    value: MutableState<String>,
    label: String,
    imageVector: ImageVector,
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
        leadingIcon = { Icon(imageVector = imageVector, contentDescription = null) },
        onValueChange = { value.value = it },
        label = { Text(text = label, fontSize = 20.sp) },
        placeholder = { Text(text = placeholder) },
        modifier = Modifier.padding(10.dp),
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
    )
}

@Composable
fun CustomElevatedBtn(text: String, onClick: () -> Unit) {
    ElevatedButton(
        onClick = { onClick() }, colors = ButtonDefaults.elevatedButtonColors(
            containerColor = Color(0xFFB34086)
        ),
        contentPadding = PaddingValues(50.dp, 20.dp),
        modifier = Modifier.padding(20.dp)
    ) {
        Text(
            text,
            color = Color(0xFFFFE642),
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
        )
    }
}
