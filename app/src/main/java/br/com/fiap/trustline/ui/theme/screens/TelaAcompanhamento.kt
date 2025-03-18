import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.vectorResource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrustlineScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Imagem no topo ("Trustline")
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

        Column(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_trustline),
                contentDescription = "Logo",
                modifier = Modifier.size(100.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

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
                InputField("Código da denúncia:")
                InputField("Status da denúncia:")
                InputField("Data da denúncia:")
                EditableField("Denúncia:")
                EditableField("Arquivos anexados:")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("outra_tela") }, // Navega para outra tela
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF8A7FCF))
        ) {
            Text("Ir para outra tela", color = Color.White)
        }
    }
}

@Composable
fun InputField(label: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        TextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White)
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun EditableField(label: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                Button(onClick = {}, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF8A7FCF))) {
                    Text("Editar", color = Color.White)
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTrustlineScreen() {
    val navController = rememberNavController()
    TrustlineScreen(navController)
}