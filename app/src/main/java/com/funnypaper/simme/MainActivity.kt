package com.funnypaper.simme

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.funnypaper.simme.data.local.dao.ProjectDao
import com.funnypaper.simme.domain.model.AudioFileModel
import com.funnypaper.simme.domain.model.BoardModel
import com.funnypaper.simme.domain.model.MetaDataModel
import com.funnypaper.simme.domain.model.PointModel
import com.funnypaper.simme.domain.model.RankModel
import com.funnypaper.simme.domain.model.TimingModel
import com.funnypaper.simme.ui.screens.projectlist.ProjectItemDetailsUIState
import com.funnypaper.simme.ui.screens.projectlist.ProjectListItemDetails
import com.funnypaper.simme.ui.theme.SIMMETheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var dao: ProjectDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SIMMETheme {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground
                ) {
                    ProjectListItemDetails(
                        isFullScreen = true,
                        item = ProjectItemDetailsUIState(
                            thumbnailUri = Uri.EMPTY,
                            title = "title",
                            description = "description",
                            author = "author",
                            timing = TimingModel(
                                bpm = 152,
                                millis = (3 * 60 + 23) * 1000,
                                offset = 0
                            ),
                            audio = AudioFileModel(
                                name = "Test Audio",
                                uri = Uri.EMPTY,
                                pcm = List(1024) { Random.nextFloat() * 1000 },
                                millis = ((1 * 60 + 3) * 60 + 23) * 1000,
                            ),
                            board = BoardModel(PointModel(-500f, -220f), 1920f, 1080f),
                            metaData = listOf(MetaDataModel("META 1"), MetaDataModel("META 2")),
                            ranks = List(3) { RankModel("name $it", it, Uri.EMPTY) }
                        )
                    )
                }
            }
        }
    }
}
