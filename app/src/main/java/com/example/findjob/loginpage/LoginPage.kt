package com.example.findjob.loginpage

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.findjob.R
import com.example.findjob.ui.theme.FindJobTheme

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0E5459)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFE642),
            ),
            shape = RoundedCornerShape(50.dp, 0.dp, 50.dp, 0.dp),
            modifier = Modifier.fillMaxSize(0.8f)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomText("Giriş Ekranı", Color(0xFFB34086), fontSize = 40.sp)
                CustomTxtField("E-Mail", Icons.Default.Email, KeyboardType.Email)
                CustomTxtField("Şifre", Icons.Default.Lock)
                CustomElevatedBtn {
                    /*TODO()*/ //webservice isteka atıp kullanıcı varmı diye bakacak
                }
                Text(
                    "Hesap Oluştur",
                    modifier = Modifier.clickable {
                        /*TODO()*/ //hesap oluştur sayfasına aktaracak
                    },
                    fontSize = 17.sp,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }
}

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
    setText: String,
    imageVector: ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    var text by remember { mutableStateOf(setText) }

    OutlinedTextField(
        value = "",
        leadingIcon = { Icon(imageVector = imageVector, contentDescription = null) },
        onValueChange = {
            text = it
        },
        label = { Text(text = text, fontSize = 20.sp) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        placeholder = { Text(text = "$text Giriniz") },
        modifier = Modifier.padding(10.dp)
    )
}

@Composable
fun CustomElevatedBtn(onClick: () -> Unit) {
    ElevatedButton(
        onClick = { onClick() }, colors = ButtonDefaults.elevatedButtonColors(
            containerColor = Color(0xFFB34086)
        ),
        contentPadding = PaddingValues(50.dp, 20.dp),
        modifier = Modifier.padding(20.dp)
    ) {
        Text(
            "Giriş Yap",
            color = Color(0xFFFFE642),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FindJobTheme {
        Greeting()
    }
}