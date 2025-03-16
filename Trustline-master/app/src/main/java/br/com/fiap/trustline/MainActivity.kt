package br.com.fiap.trustline

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.trustline.ui.screens.LoginScreen
import br.com.fiap.trustline.ui.theme.TrustlineTheme
import br.com.fiap.trustline.ui.theme.screens.DenunciaScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrustlineTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF6F6F6)
                ) {
                    TrustlineNavHost()
                }
            }
        }
    }
}

@Composable
fun TrustlineNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(
                onLoginClick = { navController.navigate("login") },
                onDenunciaClick = { navController.navigate("DenunciaScreen") }
            )
        }
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("DenunciaScreen") {
            DenunciaScreen(navController)
        }
    }
}

@Composable
fun MainScreen(
    onLoginClick: () -> Unit,
    onDenunciaClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Espaçamento superior
        Spacer(modifier = Modifier.height(16.dp))

        // Logo
        Image(
            painter = painterResource(id = R.drawable.logo_trustline),
            contentDescription = "Logo Trustline",
            modifier = Modifier.size(120.dp)
        )

        // Caixa azul com texto e ícones
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(1f),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(width = 6.dp, color = Color(0xFF9B9191)),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF22167E)) // Azul escuro
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Ícone de cadeado
                Icon(
                    painter = painterResource(id = R.drawable.ic_white_lock),
                    contentDescription = "Ícone de Segurança",
                    modifier = Modifier.size(40.dp),
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Texto principal
                Text(
                    text = "Faça denúncias de forma anônima e segura. Protegemos sua identidade com criptografia avançada e oferecemos um canal confidencial para relatar irregularidades.",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFF5F5F5),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Ícone de voz
                Icon(
                    painter = painterResource(id = R.drawable.ic_voice),
                    contentDescription = "Ícone de Voz",
                    modifier = Modifier.size(40.dp),
                    tint = Color.Unspecified
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Texto adicional
                Text(
                    text = "Ajudando a tornar sua empresa um lugar seguro e confiável para qualquer pessoa.",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFF5F5F5),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }

        // Botões na parte inferior
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onDenunciaClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(text = "Denúncia Anônima", color = Color.Black)
            }

            Button(
                onClick = onLoginClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(text = "Login", color = Color.White)
            }
        }

        // Espaçamento inferior
        Spacer(modifier = Modifier.height(16.dp))
    }
}