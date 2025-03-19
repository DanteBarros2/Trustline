import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.trustline.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AcompanhamentoScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F6))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Barra superior com botão de voltar
        TopBar(navController)

        // Logo Trustline
        Image(
            painter = painterResource(id = R.drawable.logo_trustline),
            contentDescription = "Logo Trustline",
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Card com informações da denúncia
        DenunciaInfoCard()

        Spacer(modifier = Modifier.height(16.dp))

        // Botão de navegação
        Button(
            onClick = { navController.navigate("outra_tela") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8A7FCF))
        ) {
            Text("Ir para outra tela", color = Color.White)
        }
    }
}

@Composable
fun TopBar(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Voltar")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DenunciaInfoCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF92B4F4), shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            InputField(label = "Código da denúncia:", value = "123456")
            InputField(label = "Status da denúncia:", value = "Em andamento")
            InputField(label = "Data da denúncia:", value = "18/03/2025")
            EditableField(label = "Denúncia:", value = "Descrição da denúncia aqui...")
            EditableField(label = "Arquivos anexados:", value = "Arquivo1.pdf, Foto2.jpg")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        TextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.White)
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun EditableField(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            Column {
                Text(text = value, fontSize = 14.sp, color = Color.Black, modifier = Modifier.weight(1f))
                Button(
                    onClick = { /* Ação de edição */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8A7FCF)),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Editar", color = Color.White)
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAcompanhamentoScreen() {
    val navController = rememberNavController()
    AcompanhamentoScreen(navController)
}
