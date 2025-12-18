package com.example.praktikum12.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh // IMPORT INI WAJIB ADA
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.praktikum12.R
import com.example.praktikum12.modeldata.DataSiswa
import com.example.praktikum12.viewmodel.HomeViewModel
import com.example.praktikum12.viewmodel.StatusUiSiswa
import com.example.praktikum12.viewmodel.provider.PenyediaViewModel
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.example.praktikum12.uicontroller.route.DestinasiHome

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(DestinasiHome.titleRes)) },
                scrollBehavior = scrollBehavior,
                actions = {
                    // Tombol Refresh di pojok kanan atas
                    IconButton(onClick = { viewModel.loadSiswa() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh Data"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.entry_siswa)
                )
            }
        }
    ) { innerPadding ->
        HomeStatus(
            statusUiSiswa = viewModel.listSiswa,
            retryAction = { viewModel.loadSiswa() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick
        )
    }
}

@Composable
fun HomeStatus(
    statusUiSiswa: StatusUiSiswa,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit
) {
    when (statusUiSiswa) {
        is StatusUiSiswa.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is StatusUiSiswa.Success -> {
            if (statusUiSiswa.siswa.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Tidak ada data siswa")
                        Button(onClick = retryAction, modifier = Modifier.padding(top = 8.dp)) {
                            Text("Refresh")
                        }
                    }
                }
            } else {
                ListSiswa(
                    listSiswa = statusUiSiswa.siswa,
                    modifier = modifier.fillMaxWidth(),
                    onItemClick = { onDetailClick(it.id) }
                )
            }
        }
        is StatusUiSiswa.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator() // Mengganti image loading agar lebih ringan
    }
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun ListSiswa(
    listSiswa: List<DataSiswa>,
    modifier: Modifier = Modifier,
    onItemClick: (DataSiswa) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(items = listSiswa, key = { it.id }) { siswa ->
            CardSiswa(
                siswa = siswa,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onItemClick(siswa) }
            )
        }
    }
}

@Composable
fun CardSiswa(
    siswa: DataSiswa,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = siswa.nama,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = siswa.nim,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = siswa.alamat,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = siswa.telpon,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}