package com.example.sevenwindsstudio.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sevenwindsstudio.AppTheme
import com.example.sevenwindsstudio.presentation.navigation.Screen
import com.example.sevenwindsstudio.presentation.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isSuccess) {
        LaunchedEffect(Unit) {
            navController.navigate(Screen.Locations.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Вход",
                        style = MaterialTheme.typography.h6.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            letterSpacing = (-0.12).sp,
                            color = AppTheme.Primary
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                backgroundColor = AppTheme.Background,
                elevation = 0.dp
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppTheme.Background)
                    .padding(padding)
            ) {
                Divider(
                    color = AppTheme.Divider,
                    thickness = 0.5.dp,
                    modifier = Modifier.fillMaxWidth()
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp)
                        .padding(top = 72.dp)
                ) {
                    OutlinedTextField(
                        value = uiState.login,
                        onValueChange = viewModel::onLoginChanged,
                        label = { Text("E-mail") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "Email",
                                tint = AppTheme.Medium
                            )
                        },
                        modifier = Modifier
                            .width(339.dp)
                            .height(73.dp)
                            .padding(bottom = 16.dp),
                        shape = RoundedCornerShape(24.5.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = AppTheme.Primary,
                            unfocusedBorderColor = AppTheme.Medium,
                            textColor = AppTheme.Primary,
                            cursorColor = AppTheme.Primary
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )

                    OutlinedTextField(
                        value = uiState.password,
                        onValueChange = viewModel::onPasswordChanged,
                        label = { Text("Пароль") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Пароль",
                                tint = AppTheme.Medium
                            )
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier
                            .width(339.dp)
                            .height(73.dp)
                            .padding(bottom = 24.dp),
                        shape = RoundedCornerShape(24.5.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = AppTheme.Primary,
                            unfocusedBorderColor = AppTheme.Medium,
                            textColor = AppTheme.Primary,
                            cursorColor = AppTheme.Primary
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )

                    if (!uiState.errorMessage.isNullOrEmpty()) {
                        Text(
                            text = uiState.errorMessage!!,
                            color = AppTheme.Error,
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier
                                .width(339.dp)
                                .padding(bottom = 16.dp),
                            textAlign = TextAlign.Center
                        )
                    }

                    Button(
                        onClick = { viewModel.login() },
                        modifier = Modifier
                            .width(338.dp)
                            .height(48.dp),
                        shape = RoundedCornerShape(24.5.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = AppTheme.Dark,
                            disabledBackgroundColor = AppTheme.Dark.copy(alpha = 0.5f),
                            contentColor = AppTheme.Light
                        ),
                        enabled = !uiState.isLoading,
                        elevation = ButtonDefaults.elevation(
                            defaultElevation = 2.dp,
                            pressedElevation = 4.dp
                        )
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp,
                                color = AppTheme.Light
                            )
                        } else {
                            Text(
                                text = "Войти",
                                style = MaterialTheme.typography.button.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    letterSpacing = (-0.14).sp,
                                    color = AppTheme.Light
                                )
                            )
                        }
                    }

                    TextButton(
                        onClick = {
                            navController.navigate(Screen.Register.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        },
                        modifier = Modifier
                            .width(339.dp)
                            .padding(top = 16.dp)
                    ) {
                        Text(
                            text = "Нет аккаунта? Зарегистрироваться",
                            style = MaterialTheme.typography.body2,
                            color = AppTheme.Primary
                        )
                    }
                }
            }
        }
    )
}