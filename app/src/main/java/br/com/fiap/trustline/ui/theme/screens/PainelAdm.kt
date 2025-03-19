import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PainelAdm(
                navController = TODO() // Substitua pelo seu NavController
            )
        }
    }
}

@Composable
fun PainelAdm(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Barra superior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF92B5F3))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Ícone de voltar
                Text(
                    text = "<",
                    fontSize = 24.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .clickable { navController.popBackStack() }
                )

                Spacer(modifier = Modifier.weight(1f))

                // Texto TRUSTLINE
                Text(
                    text = "TRUSTLINE",
                    fontSize = 30.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Campos de informações
        InfoBox("Denúncias realizadas:", "300")
        InfoBox("Denúncias em análise:", "100", isSelected = true)
        InfoBox("Denúncias aceitas:", "150")
        InfoBox("Denúncias negadas:", "50")
    }
}

@Composable
fun InfoBox(label: String, value: String, isSelected: Boolean = false) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, fontSize = 16.sp, color = Color.Black)
        Box(
            modifier = Modifier
                .padding(4.dp)
                .width(300.dp)
                .height(40.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                .border(if (isSelected) 2.dp else 0.dp, Color.Blue, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = value, fontSize = 18.sp, color = Color.Black)
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}
