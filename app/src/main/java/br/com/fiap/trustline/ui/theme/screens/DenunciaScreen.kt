package br.com.fiap.trustline.ui.theme.screens

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import br.com.fiap.trustline.R


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun DenunciaScreen(navController: NavController) {
    var categoriaSelecionada by remember { mutableStateOf("") }
    val categorias = listOf("Fraude", "Assédio", "Corrupção", "Outros")
    var descricao by remember { mutableStateOf("") }
    var uriArquivo by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    // Lançador para escolher um arquivo
    val getContent = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                uriArquivo = it
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
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
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_trustline),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(100.dp)
            )
        }
        // Título "Denúncia Segura"
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,

            ) {
            Text(
                "Denúncia Segura",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }

        // Texto "Selecione a Categoria"
        Text(
            "Selecione a Categoria:",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 8.dp)
        )

        // Seleção de Categoria utilizando RadioButton
        categorias.forEach { categoria ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp)
            ) {
                RadioButton(
                    selected = categoriaSelecionada == categoria,
                    onClick = { categoriaSelecionada = categoria },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    text = categoria,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        // Campo de Descrição
        OutlinedTextField(
            value = descricao,
            onValueChange = { descricao = it },
            label = { Text("Descrição") },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp),
            maxLines = 5,
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                textColor = MaterialTheme.colorScheme.onSurface
            )
        )

        // Upload de Arquivo com Ícone
        Button(
            onClick = { getContent.launch("image/*") },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.upload), // Ícone de upload
                contentDescription = "Ícone de Upload",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "Selecionar Arquivo",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
            )
        }

        // Mostrar arquivo selecionado
        uriArquivo?.let {
            Text("Arquivo Selecionado: ${it.path}", style = MaterialTheme.typography.bodySmall)
        }

        // Botão de Envio com Ícone
        Button(
            onClick = {
                Toast.makeText(context, "Denúncia Enviada!", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text(
                "Enviar Denúncia",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
            )
        }
    }
}
