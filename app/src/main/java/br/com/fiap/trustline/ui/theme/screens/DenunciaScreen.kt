package br.com.fiap.trustline.ui.theme.screens

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.trustline.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.UUID
import androidx.core.content.ContextCompat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DenunciaScreen(navController: NavController) {
    var categoriaSelecionada by remember { mutableStateOf("") }
    val categorias = listOf("Fraude", "Assédio", "Corrupção", "Outros")
    var descricao by remember { mutableStateOf("") }
    var uriArquivo by remember { mutableStateOf<Uri?>(null) }
    var isUploading by remember { mutableStateOf(false) }
    var permissaoNegadaDefinitivamente by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val permissaoNecessaria = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val getContent = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            uriArquivo = it
            Toast.makeText(context, "Imagem selecionada: ${it.lastPathSegment}", Toast.LENGTH_SHORT).show()
        } ?: run {
            Toast.makeText(context, "Falha ao selecionar imagem", Toast.LENGTH_SHORT).show()
            Log.e("DenunciaScreen", "Falha ao selecionar URI: $uri")
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(context, "Permissão concedida!", Toast.LENGTH_SHORT).show()
            getContent.launch("image/*")
        } else {
            permissaoNegadaDefinitivamente = true
            Toast.makeText(context, "Permissão negada. Vá até Configurações para ativar.", Toast.LENGTH_LONG).show()
        }
    }

    fun uploadImagemParaFirebase(uri: Uri, context: Context) {
        val storage = Firebase.storage
        val storageRef = storage.reference
        val fileRef = storageRef.child("denuncias/${UUID.randomUUID()}.jpg")

        fileRef.putFile(uri)
            .addOnSuccessListener {
                fileRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    Toast.makeText(context, "Upload concluído: $downloadUri", Toast.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Erro ao fazer upload: ${exception.message}", Toast.LENGTH_LONG).show()
            }
    }

    fun abrirConfiguracoes() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
        }
        context.startActivity(intent)
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        IconButton(onClick = { navController.popBackStack() }, modifier = Modifier.align(Alignment.Start)) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Voltar")
        }

        Image(
            painter = painterResource(id = R.drawable.logo_trustline),
            contentDescription = "Logo",
            modifier = Modifier.size(100.dp).align(Alignment.CenterHorizontally)
        )

        Text("Denúncia Segura", style = MaterialTheme.typography.headlineMedium)
        Text("Selecione a Categoria:", style = MaterialTheme.typography.bodyMedium)

        categorias.forEach { categoria ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = categoriaSelecionada == categoria, onClick = { categoriaSelecionada = categoria })
                Text(text = categoria, style = MaterialTheme.typography.bodyLarge)
            }
        }

        OutlinedTextField(value = descricao, onValueChange = { descricao = it }, label = { Text("Descrição") }, modifier = Modifier.fillMaxWidth())

        Button(
            onClick = {
                val permissaoConcedida = ContextCompat.checkSelfPermission(context, permissaoNecessaria) == PackageManager.PERMISSION_GRANTED
                if (!permissaoConcedida) {
                    permissionLauncher.launch(permissaoNecessaria)
                } else {
                    getContent.launch("image/*")
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Icon(painter = painterResource(id = R.drawable.upload), contentDescription = "Ícone de Upload", tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Selecionar Arquivo", color = Color.White)
        }

        uriArquivo?.let { Text("Arquivo Selecionado: ${it.lastPathSegment ?: "Imagem selecionada"}") }

        Button(
            onClick = {
                if (uriArquivo == null) {
                    Toast.makeText(context, "Selecione uma imagem primeiro", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                uriArquivo?.let { uploadImagemParaFirebase(it, context) }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
            enabled = !isUploading
        ) {
            if (isUploading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
            } else {
                Text("Enviar Denúncia", color = Color.White)
            }
        }

        if (permissaoNegadaDefinitivamente) {
            Button(
                onClick = { abrirConfiguracoes() },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Abrir Configurações", color = Color.White)
            }
        }
    }
}