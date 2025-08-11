package com.mosiuk.funnycombination.ui.privacy

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mosiuk.funnycombination.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.privacy_policy)) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            item {
                Text(
                    text = "Privacy Policy for Funny Combination",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            item {
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Last updated: August 11, 2025",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            item {
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "This Privacy Policy describes Our policies and procedures on the collection, use and disclosure of Your information when You use the Application.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            item {
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "Information We Collect",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            item {
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Funny Combination is an offline game. We do not collect, store, or transmit any personal data from your device. Your high scores are stored locally on your device and are not shared with us or any third parties.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            item {
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "Changes to this Privacy Policy",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            item {
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "We may update Our Privacy Policy from time to time. We will notify You of any changes by posting the new Privacy Policy on this page.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
