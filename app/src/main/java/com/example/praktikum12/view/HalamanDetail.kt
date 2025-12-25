package com.example.praktikum12.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect // Tambahan import
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.praktikum12.R
import com.example.praktikum12.modeldata.DataSiswa
import com.example.praktikum12.viewmodel.DetailViewModel
import com.example.praktikum12.viewmodel.StatusUIDetail
import com.example.praktikum12.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailSiswaScreen(
    navigateBack: () -> Unit,
    navigateToEditItem: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    // --- TAMBAHAN: Refresh data otomatis saat masuk halaman Detail ---
    LaunchedEffect(Unit) {
        viewModel.getSatuSiswa()
    }
    // ----------------------------------------------------------------

    Scaffold(
        topBar = {
            SiswaTopAppBar(
                title = stringResource(R.string.detail_siswa),
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = { viewModel.getSatuSiswa() } // Opsional: refresh manual
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (viewModel.statusUIDetail is StatusUIDetail.Success) {
                        navigateToEditItem((viewModel.statusUIDetail as StatusUIDetail.Success).satuSiswa.id)
                    }
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_siswa)
                )
            }
        },
    ) { innerPadding ->
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

        BodyDetailSiswa(
            statusDetail = viewModel.statusUIDetail,
            retryAction = viewModel::getSatuSiswa,
            onDeleteClick = {
                deleteConfirmationRequired = true
            },
            modifier = Modifier.padding(innerPadding)
        )

        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    coroutineScope.launch {
                        viewModel.hapusSatuSiswa()
                        navigateBack()
                    }
                },
                onDeleteCancel = { deleteConfirmationRequired = false }
            )
        }
    }
}

@Composable
fun BodyDetailSiswa(
    statusDetail: StatusUIDetail,
    retryAction: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (statusDetail) {
        is StatusUIDetail.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is StatusUIDetail.Error -> {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(R.string.loading_failed))
                Button(
                    onClick = retryAction,
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(stringResource(R.string.retry))
                }
            }
        }
        is StatusUIDetail.Success -> {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ItemDetailSiswa(
                    siswa = statusDetail.satuSiswa,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedButton(
                    onClick = onDeleteClick,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.delete))
                }
            }
        }
    }
}

@Composable
fun ItemDetailSiswa(
    siswa: DataSiswa,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ComponentDetailSiswa(judul = "Nama", isinya = siswa.nama)
            ComponentDetailSiswa(judul = "Alamat", isinya = siswa.alamat)
            ComponentDetailSiswa(judul = "Telpon", isinya = siswa.telpon)
        }
    }
}

@Composable
fun ComponentDetailSiswa(
    judul: String,
    isinya: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "$judul:",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.outline
        )
        Text(
            text = isinya,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(R.string.delete_confirmation)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = stringResource(R.string.yes))
            }
        }
    )
}