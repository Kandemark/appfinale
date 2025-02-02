package com.application.myapplication.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.application.myapplication.common.CustomDefaultBtn
import com.application.myapplication.ui.theme.TextColor
import com.application.myapplication.R

@Composable
fun LoginSuccessScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Login Success",
                color = MaterialTheme.colors.TextColor,
                fontWeight = FontWeight(700),
                fontSize = 18.sp
            )
        }
        Image(
            painter = painterResource(id = R.drawable.success),
            contentDescription = "Login Success Image"
        )
        Text(
            text = "Login Successful",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(50.dp))

        CustomDefaultBtn(shapeSize = 50f, btnText = "Continue") {
            navController.navigate("dashboard")
        }
    }


}