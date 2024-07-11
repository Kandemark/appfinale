package com.application.myapplication.ui.screens.signup

import android.util.Patterns
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.background
import com.application.myapplication.R
import com.application.myapplication.common.CustomDefaultBtn
import com.application.myapplication.common.CustomTextField
import com.application.myapplication.common.DefaultBackArrow
import com.application.myapplication.common.ErrorSuggestion
import com.application.myapplication.ui.theme.PrimaryColor
import com.application.myapplication.ui.theme.PrimaryLightColor
import com.application.myapplication.ui.theme.TextColor

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SignUpScreen(navController: NavController) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var confirmPass by remember { mutableStateOf(TextFieldValue("")) }
    var firstName by remember { mutableStateOf(TextFieldValue("")) }
    var lastName by remember { mutableStateOf(TextFieldValue("")) }
    var phoneNumber by remember { mutableStateOf(TextFieldValue("")) }
    var address by remember { mutableStateOf(TextFieldValue("")) }
    val emailErrorState = remember { mutableStateOf(false) }
    val passwordErrorState = remember { mutableStateOf(false) }
    val conPasswordErrorState = remember { mutableStateOf(false) }
    val firstNameErrorState = remember { mutableStateOf(false) }
    val lastNameErrorState = remember { mutableStateOf(false) }
    val phoneNumberErrorState = remember { mutableStateOf(false) }
    val addressErrorState = remember { mutableStateOf(false) }
    val animate = remember { mutableStateOf(true) }


    AnimatedContent(targetState = animate.value, transitionSpec = {
        slideInHorizontally(
            initialOffsetX = { value ->
                value
            }
        ) with slideOutHorizontally(
            targetOffsetX = { value ->
                -value
            }
        )
    }, label = "AnimatedContent") {
        if (it) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    Box(modifier = Modifier.weight(0.7f)) {
                        DefaultBackArrow {
                            navController.popBackStack()
                        }
                    }
                    Box(modifier = Modifier.weight(1.0f)) {
                        Text(
                            text = "Sign Up",
                            color = MaterialTheme.colors.TextColor,
                            fontSize = 18.sp
                        )
                    }


                }
                Spacer(modifier = Modifier.height(50.dp))
                Text(text = "Register Account", fontSize = 26.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = "Complete your details or continue\nwith social media.",
                    color = MaterialTheme.colors.TextColor,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(50.dp))
                CustomTextField(
                    placeholder = "example@email.com",
                    trailingIcon = R.drawable.mail,
                    label = "Email",
                    errorState = emailErrorState,
                    keyboardType = KeyboardType.Email,
                    visualTransformation = VisualTransformation.None,
                    onChanged = { newEmail ->
                        email = newEmail
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))
                CustomTextField(
                    placeholder = "********",
                    trailingIcon = R.drawable.lock,
                    label = "Password",
                    keyboardType = KeyboardType.Password,
                    errorState = passwordErrorState,
                    visualTransformation = PasswordVisualTransformation(),
                    onChanged = { newPass ->
                        password = newPass
                    }
                )


                Spacer(modifier = Modifier.height(20.dp))
                CustomTextField(
                    placeholder = "********",
                    trailingIcon = R.drawable.lock,
                    label = "Confirm Password",
                    keyboardType = KeyboardType.Password,
                    errorState = conPasswordErrorState,
                    visualTransformation = PasswordVisualTransformation(),
                    onChanged = { newPass ->
                        confirmPass = newPass
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))
                if (emailErrorState.value) {
                    ErrorSuggestion("Please enter valid email address.")
                }
                if (passwordErrorState.value) {
                    Row() {
                        ErrorSuggestion("Please enter valid password.")
                    }
                }
                if (conPasswordErrorState.value) {
                    ErrorSuggestion("Confirm Password miss matched.")
                }
                CustomDefaultBtn(shapeSize = 50f, btnText = "Continue") {
                    //email pattern
                    val pattern = Patterns.EMAIL_ADDRESS
                    val isEmailValid = pattern.matcher(email.text).matches()
                    val isPassValid = password.text.length >= 8
                    val conPassMatch = password == confirmPass
                    emailErrorState.value = !isEmailValid
                    passwordErrorState.value = !isPassValid
                    conPasswordErrorState.value = !conPassMatch
                    if (isEmailValid && isPassValid && conPassMatch) {
                        animate.value = !animate.value
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 50.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 10.dp,
                            alignment = Alignment.CenterHorizontally
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .background(
                                    MaterialTheme.colors.PrimaryLightColor,
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.google_icon),
                                contentDescription = "Google Login Icon"
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .background(
                                    MaterialTheme.colors.PrimaryLightColor,
                                    shape = CircleShape
                                )
                                .clickable {

                                },
                            contentAlignment = Alignment.Center,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.twitter),
                                contentDescription = "Twitter Login Icon"
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .background(
                                    MaterialTheme.colors.PrimaryLightColor,
                                    shape = CircleShape
                                )
                                .clickable {

                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.facebook_2),
                                contentDescription = "Facebook Login Icon"
                            )
                        }

                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 30.dp)
                            .clickable {

                            },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "By continuing you confirm that you agree",
                            color = MaterialTheme.colors.TextColor
                        )
                        Row()
                        {
                            Text(
                                text = "with our ",
                                color = MaterialTheme.colors.TextColor,
                            )
                            Text(
                                text = "Terms & Condition",
                                color = MaterialTheme.colors.PrimaryColor,
                                modifier = Modifier.clickable {

                                })
                        }

                    }
                }


            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    Box(modifier = Modifier.weight(0.7f)) {
                        DefaultBackArrow {
                            animate.value = !animate.value
                        }
                    }
                    Box(modifier = Modifier.weight(1.0f)) {
                        Text(
                            text = "Sign Up",
                            color = MaterialTheme.colors.TextColor,
                            fontSize = 18.sp
                        )
                    }


                }
                Spacer(modifier = Modifier.height(50.dp))
                Text(text = "Complete Profile", fontSize = 26.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = "Complete your details or continue\nwith social media.",
                    color = MaterialTheme.colors.TextColor,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(50.dp))
                CustomTextField(
                    placeholder = "Enter your first name",
                    trailingIcon = R.drawable.user,
                    label = "First Name",
                    errorState = firstNameErrorState,
                    keyboardType = KeyboardType.Text,
                    visualTransformation = VisualTransformation.None,
                    onChanged = { newText ->
                        firstName = newText
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                CustomTextField(
                    placeholder = "Enter your last name",
                    trailingIcon = R.drawable.user,
                    label = "Last Name",
                    errorState = lastNameErrorState,
                    keyboardType = KeyboardType.Text,
                    visualTransformation = VisualTransformation.None,
                    onChanged = { newText ->
                        lastName = newText
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))
                CustomTextField(
                    placeholder = "Enter your phone number",
                    trailingIcon = R.drawable.phone,
                    label = "Phone Number",
                    keyboardType = KeyboardType.Phone,
                    errorState = phoneNumberErrorState,
                    visualTransformation = VisualTransformation.None,
                    onChanged = { newNumber ->
                        phoneNumber = newNumber
                    }
                )


                Spacer(modifier = Modifier.height(20.dp))
                CustomTextField(
                    placeholder = "example: Dhaka, Bangladesh",
                    trailingIcon = R.drawable.location_point,
                    label = "Address",
                    keyboardType = KeyboardType.Password,
                    errorState = addressErrorState,
                    visualTransformation = VisualTransformation.None,
                    onChanged = { newText ->
                        address = newText
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                if (firstNameErrorState.value || lastNameErrorState.value) {
                    ErrorSuggestion("Please enter valid name.")
                }
                if (phoneNumberErrorState.value) {
                    ErrorSuggestion("Please enter valid phone number.")
                }
                if (addressErrorState.value) {
                    ErrorSuggestion("Please enter valid address.")
                }

                CustomDefaultBtn(shapeSize = 50f, btnText = "Continue") {
                    val isPhoneValid = phoneNumber.text.isEmpty() || phoneNumber.text.length < 4
                    val isFNameValid = firstName.text.isEmpty() || firstName.text.length < 3
                    val isLNameValid = lastName.text.isEmpty() || lastName.text.length < 3
                    val isAddressValid = address.text.isEmpty() || address.text.length < 5
                    firstNameErrorState.value = !isFNameValid
                    lastNameErrorState.value = !isLNameValid
                    addressErrorState.value = !isAddressValid
                    phoneNumberErrorState.value = !isPhoneValid
                    if (!isFNameValid && !isLNameValid && !isAddressValid && !isPhoneValid) {
                        navController.navigate("Otp")
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 50.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp)
                        .clickable {

                        },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "By continuing you confirm that you agree",
                        color = MaterialTheme.colors.TextColor
                    )
                    Row()
                    {
                        Text(
                            text = "with our ",
                            color = MaterialTheme.colors.TextColor,
                        )
                        Text(
                            text = "Terms & Condition",
                            color = MaterialTheme.colors.PrimaryColor,
                            modifier = Modifier.clickable {

                            })
                    }

                }
            }


        }
    }
}


