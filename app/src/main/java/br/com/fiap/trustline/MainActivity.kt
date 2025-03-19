package br.com.fiap.trustline

import AcompanhamentoScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.trustline.R
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
                onDenunciaClick = { navController.navigate("denuncia") },
                onAcompanharClick = { navController.navigate("acompanhamento") }
            )
        }
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("denuncia") {
            DenunciaScreen(navController = navController)
        }
        composable("acompanhamento") {
            AcompanhamentoScreen(navController = navController)
        }
    }
}

@Composable
fun MainScreen(
    onLoginClick: () -> Unit,
    onDenunciaClick: () -> Unit,
    onAcompanharClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Logo e texto superior
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_trustline),
                contentDescription = "Logo Trustline",
                modifier = Modifier.size(100.dp)
            )
            Text(
                text = "Denuncie com Segurança. Sua voz importa",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF22167E),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // Card central
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(1f),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(width = 2.dp, color = Color(0xFF9B9191)),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF22167E))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Ícone de cadeado
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Ícone de Anonimato",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Faça denúncias de forma anônima e segura.",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.padding(14.dp))
                // Ícone de perfil falante
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Ícone de Voz",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Protegemos sua identidade com criptografia avançada e oferecemos um canal confidencial para relatar irregularidades.",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.padding(14.dp))
                // Texto adicional
                Text(
                    text = "Ajudando a tornar sua empresa um lugar seguro e confiável para qualquer pessoa.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        // Botões inferiores
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                MainButton("Login", Color.Gray, Color.Black, onLoginClick)
                MainButton("Denúncia Anônima", Color(0xFF22167E), Color.White, onDenunciaClick)
            }
            MainButton("Acompanhar Denúncia", Color.Gray, Color.Black, onAcompanharClick)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun MainButton(text: String, backgroundColor: Color, textColor: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .width(150.dp) // Tamanho fixo para os botões
            .height(48.dp)
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

