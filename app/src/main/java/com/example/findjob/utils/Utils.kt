package com.example.findjob.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.findjob.model.GetJobs

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

@Composable
fun DialogWithImage(
    onDismissRequest: () -> Unit,
    onConfirmation: (jobTitle: String, jobDesc: String, jobPrice: String, checked: Boolean) -> Unit,
    model: Any?,
    jobs: GetJobs,
    check: Boolean
) {
    var jobTitle by remember { mutableStateOf(jobs.jobTitle) }
    var jobDesc by remember { mutableStateOf(jobs.jobDesc) }
    var jobPrice by remember { mutableStateOf(jobs.jobPrice) }
    var checked by remember { mutableStateOf(check) }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(480.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(5.dp)
                        .fillMaxWidth()
                ) {
                    Checkbox(
                        colors = if (checked) CheckboxDefaults.colors(
                            checkedColor = Color(
                                0xFF22bb33
                            )
                        ) else CheckboxDefaults.colors(
                            uncheckedColor = Color.Red
                        ),
                        checked = checked,
                        onCheckedChange = { checked = it },
                    )
                    Text(
                        text = if (checked) "Yayında" else "Yayında Değil",
                        modifier = Modifier.align(Alignment.CenterVertically),
                        color = Color.Gray
                    )
                }
                AsyncImage(
                    model = model,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(150.dp)
                )
                TextField(
                    value = jobTitle,
                    onValueChange = { jobTitle = it },
                    label = { Text("İş Başlığı") }
                )
                TextField(
                    value = jobDesc,
                    onValueChange = { jobDesc = it },
                    label = { Text("İş Açıklaması") }
                )
                TextField(
                    value = jobPrice,
                    onValueChange = { jobPrice = it },
                    label = { Text("İş Fiyatı") }
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(color = Color.Red, text = "İptal Et")
                    }
                    TextButton(
                        onClick = { onConfirmation(jobTitle, jobDesc, jobPrice, checked) },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(color = Color(0xFF22bb33), text = "Güncelle")
                    }
                }
            }
        }
    }
}
