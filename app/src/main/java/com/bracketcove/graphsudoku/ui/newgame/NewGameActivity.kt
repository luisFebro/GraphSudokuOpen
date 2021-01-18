package com.bracketcove.graphsudoku.ui.newgame

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import com.bracketcove.graphsudoku.common.getStringRes
import com.bracketcove.graphsudoku.common.makeToast
import com.bracketcove.graphsudoku.domain.Messages
import com.bracketcove.graphsudoku.ui.activegame.ActiveGameActivity
import com.bracketcove.graphsudoku.ui.newgame.buildlogic.buildNewGameLogic

/**
 * This feature is so simple that it is not even worth it to have a separate logic class
 */
class NewGameActivity : AppCompatActivity(), NewGameContainer {
    private lateinit var logic: NewGameLogic


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = NewGameViewModel()

        setContent {
            NewGameScreen(
                onEventHandler = {
                    logic.onEvent(it)
                },
                viewModel
            )
        }

        logic = buildNewGameLogic(this, viewModel, applicationContext)

    }

    override fun onStart() {
        super.onStart()
        logic.onEvent(NewGameEvent.OnStart)
    }

    override fun showMessage(message: Messages) = makeToast(getStringRes(message))

    override fun onDoneClick() {
        startActiveGameActivity()
    }

    /**
     * I want the other feature to be completely restarted each time
     */
    override fun onBackPressed() {
        startActiveGameActivity()
    }

    private fun startActiveGameActivity() {
        startActivity(
            Intent(
                this,
                ActiveGameActivity::class.java
            ).apply {
                this.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
            }
        )
    }

}