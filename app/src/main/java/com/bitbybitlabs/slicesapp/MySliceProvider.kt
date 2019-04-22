package com.bitbybitlabs.slicesapp

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.slice.Slice
import androidx.slice.SliceProvider
import androidx.slice.builders.ListBuilder
import androidx.slice.builders.SliceAction
import androidx.slice.builders.cell
import androidx.slice.builders.gridRow
import androidx.slice.builders.header
import androidx.slice.builders.inputRange
import androidx.slice.builders.list
import androidx.slice.builders.range
import androidx.slice.builders.row
import androidx.slice.builders.seeMoreCell
import androidx.slice.builders.seeMoreRow

class MySliceProvider : SliceProvider() {
    companion object {
        const val TAG = "Pokemon Slice"
    }

    override fun onCreateSliceProvider(): Boolean {
        // do some initialization if needed
        return true
    }

    override fun onBindSlice(sliceUri: Uri): Slice? {
        val activityAction = createActivityAction()
        return when (sliceUri.path) {
            "/hello" -> createSlice(sliceUri)
            "/bright" -> createBrightnessSlice(sliceUri)
            "/pokeslice" -> createPokemonSlice(sliceUri)
            "/count" -> createDynamicSlice(sliceUri)
            "/bigslice" -> createBigSlice(sliceUri)
            "/builder" -> createSliceWithBuilder(sliceUri)
        else ->
            list(context!!, sliceUri, ListBuilder.INFINITY) {
                row {
                    title = "URI not recognized"
                    primaryAction = activityAction
                }
            }
        }
    }

    override fun onMapIntentToUri(intent: Intent): Uri {
        if (intent.data != null)
            return intent.data!!
        return Uri.parse("slice-content://com.bitbybitlabs.slicesapp/pokeslice")
    }

    private fun createSlice(sliceUri: Uri): Slice {
        val activityAction = createActivityAction()
        return list(context!!, sliceUri, ListBuilder.INFINITY) {
            row {
                title = "Hello Slice World"
                primaryAction = activityAction
            }
        }
    }

    private fun createBrightnessSlice(sliceUri: Uri): Slice {
        val toggleAction =
                SliceAction.createToggle(
                    createToggleIntent("Item clicked"),
                    "Toggle adaptive brightness",
                    false
                )
        return list(context!!, sliceUri, ListBuilder.INFINITY) {
            setAccentColor((ContextCompat.getColor(context, R.color.colorPrimary)))
            row {
                title = "Settings"
                subtitle = "Go to Settings"
                primaryAction = createLaunchSettingsAction()
            }
            row {
                title = "Adaptive brightness"
                subtitle = "Optimizes brightness for available light"
                primaryAction = toggleAction
            }
            inputRange {
                max = 100
                value = 80
                inputAction = updateBrightness()
            }
        }
    }

    private fun createSliceWithBuilder(sliceUri: Uri): Slice {
        val activityAction = createActivityAction()
        return ListBuilder(context!!, sliceUri, ListBuilder.INFINITY)
            .addRow {
                it.title = "Hello Slice World Builder"
                it.primaryAction = activityAction
            }
            .build()
    }

    private fun createDynamicSlice(sliceUri: Uri): Slice {
        val toastAndIncrementAction = SliceAction.create(
            updateBrightness(),
            IconCompat.createWithResource(context, android.R.drawable.ic_menu_preferences),
            ListBuilder.ICON_IMAGE,
            "Increment."
        )
        return list(context!!, sliceUri, ListBuilder.INFINITY) {
            row {
                primaryAction = toastAndIncrementAction
                title = "Count: ${MyBroadcastReceiver.receivedCount}"
                subtitle = "Click me"
            }
        }
    }

    private fun createPokemonSlice(sliceUri: Uri): Slice {
        val pokemonList = Pokemon.createPokemonList()
        Log.d("Pokemon Slice", "Pokemon List size: $pokemonList.size.toString()")
        return ListBuilder(context!!, sliceUri, ListBuilder.INFINITY)
            .setAccentColor((ContextCompat.getColor(context, R.color.colorPrimary)))
            .addAction(createToggleNotificationAction())
            .setHeader {
                it.apply {
                    title = "Gotta catch `em all!"
                    subtitle = "I want to be the very best!"
                    summary = "I want to be the very best!"
                    primaryAction = createViewAllPokemonAction()
                }
            }
            .addRow {
                it.setTitle("Loading Title", true)
            }
            .createPokemonGridRows(pokemonList)
            .setSeeMoreRow {
                it.apply {
                    title = "See all Pokemon"
                    addEndItem(IconCompat.createWithResource(context, R.drawable.pokeball), ListBuilder.ICON_IMAGE)
                    primaryAction = createViewAllPokemonAction()
                }
            }
            .build()
    }

    private fun createBigSlice(sliceUri: Uri): Slice {
        return list(context, sliceUri, ListBuilder.INFINITY) {
            setAccentColor((ContextCompat.getColor(context, R.color.colorAccent)))
            addAction(createActivityAction())
            header {
                title = "Header Title"
                subtitle = "Header Subtitle"
                summary = "Header Summary"
                primaryAction = createActivityAction()
            }
            row {
                title = "Row with end item"
                addEndItem(IconCompat.createWithResource(context, NotificationSettings.getIcon()), ListBuilder.ICON_IMAGE)
                primaryAction = createToggleNotificationAction()
            }
            row {
                title = "Normal Row Title"
                subtitle = "Start Service Row"
                primaryAction = createServiceAction()
            }
            row {
                title = "Row with title item"
                setTitleItem(IconCompat.createWithResource(context, R.drawable.ic_notification_on), ListBuilder.ICON_IMAGE)
                primaryAction = createToggleAction("Clicked Toggle Row")
            }
            row {
                title = "Row with both"
                subtitle = "Yes, you can have both"
                setTitleItem(IconCompat.createWithResource(context, R.drawable.ic_notification_on), ListBuilder.ICON_IMAGE)
                addEndItem(IconCompat.createWithResource(context, R.drawable.ic_notification_off), ListBuilder.ICON_IMAGE)
                addEndItem(IconCompat.createWithResource(context, R.drawable.ic_launcher_foreground), ListBuilder.ICON_IMAGE)
                addEndItem(IconCompat.createWithResource(context, R.drawable.bulbasaur), ListBuilder.ICON_IMAGE)
                primaryAction = createToggleAction("Clicked Toggle Row")
            }
            inputRange {
                title = "Input Range"
                subtitle = "Input Range subtitle"
                inputAction = updateBrightness()
                max = 100
                value = 60
            }
            range {
                title = "Range"
                subtitle = "Range subtitle"
                primaryAction = createActivityAction()
                max = 100
                value = 40
            }
            gridRow {
                addCell {
                    it.apply {
                        contentIntent = createPokemonIntent("https://bulbapedia.bulbagarden.net/wiki/Pok%C3%A9mon_(species)")
                        addTitleText("Grid Cell Title")
                        addText("Small Image")
                        addImage(IconCompat.createWithResource(context, R.drawable.bulbasaur), ListBuilder.SMALL_IMAGE)
                    }
                }
                addCell {
                    it.apply {
                        contentIntent = createPokemonIntent("https://bulbapedia.bulbagarden.net/wiki/Pok%C3%A9mon_(species)")
                        addTitleText("Grid Cell Title 2")
                        addText("Large Image")
                        addImage(IconCompat.createWithResource(context, R.drawable.ivysaur), ListBuilder.LARGE_IMAGE)
                    }
                }
                cell {
                    addText("Icon Image")
                    addImage(IconCompat.createWithResource(context, R.drawable.venusaur), ListBuilder.ICON_IMAGE)
                    contentIntent = createPokemonIntent("https://bulbapedia.bulbagarden.net/wiki/Pok%C3%A9mon_(species)")
                }
                cell {
                    addText("Icon Image")
                    addImage(IconCompat.createWithResource(context, R.drawable.charmander), ListBuilder.ICON_IMAGE)
                    contentIntent = createPokemonIntent("https://bulbapedia.bulbagarden.net/wiki/Pok%C3%A9mon_(species)")
                }
                cell {
                    addText("Icon Image")
                    addImage(IconCompat.createWithResource(context, R.drawable.charmeleon), ListBuilder.ICON_IMAGE)
                    contentIntent = createPokemonIntent("https://bulbapedia.bulbagarden.net/wiki/Pok%C3%A9mon_(species)")
                }
                // shows when there isn't enough room to display all the cells
                seeMoreCell {
                    addTitleText("See More Title")
                    addText("See More")
                    contentIntent = createPokemonIntent("https://bulbapedia.bulbagarden.net/wiki/Pok%C3%A9mon_(species)")
                }
            }
            // TODO: implement an "isLoading()" example
            // only shown when not all the rows can be displayed
            seeMoreRow {
                title = "See More"
                primaryAction = createActivityAction()
            }
        }
    }

    private fun createPokemonIntent(url: String): PendingIntent {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        return PendingIntent.getActivity(context, 0, intent, 0)
    }

    private fun createToggleIntent(s: String): PendingIntent {
        val intent = Intent(context, MyBroadcastReceiver::class.java)
            .putExtra(MyBroadcastReceiver.EXTRA_MESSAGE, s)
        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }

    private fun updateBrightness(): PendingIntent {
        val intent = Intent(context, MyBroadcastReceiver::class.java)
//            .putExtra(MyBroadcastReceiver.EXTRA_BRIGHTNESS, brightness) -- input value passed as "android.app.slice.extra.RANGE_VALUE"
        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }

    private fun createActivityAction(): SliceAction {
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra("com.bitbybitlabs.slicesapp.EXTRA_TOAST_MESSAGE", "Came from Slice")
        return SliceAction.create(
            PendingIntent.getActivity(context, 0, intent, 0),
            IconCompat.createWithResource(context, R.drawable.ic_launcher_foreground),
            ListBuilder.ICON_IMAGE,
            "Enter app"
        )
    }

    private fun createServiceAction(): SliceAction {
        val intent = HelloIntentService.startActionFoo(context)
        return SliceAction.create(
            PendingIntent.getService(context, 0, intent, 0),
            IconCompat.createWithResource(context, android.R.drawable.ic_menu_search),
            ListBuilder.ICON_IMAGE,
            "Start Service"
        )
    }

    private fun createLaunchSettingsAction(): SliceAction {
//        val intent = Intent(android.provider.Settings.ACTION_DISPLAY_SETTINGS)
        val intent = Intent(context, BrightnessActivity::class.java)
        return SliceAction.create(
            PendingIntent.getActivity(context, 0, intent, 0),
            IconCompat.createWithResource(context, android.R.drawable.ic_menu_preferences),
            ListBuilder.ICON_IMAGE,
            "Launch Settings"
        )
    }

    private fun createViewAllPokemonAction(): SliceAction {
        return SliceAction.create(
            createPokemonIntent("https://bulbapedia.bulbagarden.net/wiki/Pok%C3%A9mon_(species)"),
            IconCompat.createWithResource(context, R.drawable.pokeball),
            ListBuilder.ICON_IMAGE,
            "Pokemon"
        )
    }

    private fun createToggleAction(s: String): SliceAction {
        return SliceAction.create(
            createToggleIntent(s),
            IconCompat.createWithResource(context, R.drawable.ic_launcher_foreground),
            ListBuilder.ICON_IMAGE,
            "Toggle"
        )
    }

    private fun createToggleNotificationAction(): SliceAction {
        return SliceAction(ToggleNotificationReceiver.createToggleNotificationPendingIntent(context!!),
            IconCompat.createWithResource(context, NotificationSettings.getIcon()),
            "Toggle Notification")
    }

    private fun requestSettingsPermission() {
        val brightnessPermissionIntent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
        brightnessPermissionIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(brightnessPermissionIntent)
    }

    private fun ListBuilder.createPokemonGridRows(pokemonList: List<Pokemon>): ListBuilder {
        Log.d(TAG, "creating Pokemon grid rows")
        val rows = if (pokemonList.size % 5 == 0) (pokemonList.size / 5) else (pokemonList.size / 5) + 1
        Log.d(TAG, "Rows: $rows")
        for (i in 0 until rows) {
            Log.d(TAG, "creating Row: $i")
            val startingIndex = i * 5
            Log.d(TAG, "Starting Index: $startingIndex")
            val endingIndex = if (startingIndex + 5 > pokemonList.size) {
                pokemonList.size - 1 } else {
                startingIndex + 5
            }
            Log.d(TAG, "Ending Index: $endingIndex")
            this.addGridRow {
                for (j in startingIndex..endingIndex) {
                    Log.d(TAG, "creating Row: $i, Cell: $j")
                    val pokemon = pokemonList[j]
                    var pokeType = ""
                    for (type in pokemon.types) {
                        pokeType = type.name.toLowerCase()
                    }
                    it.addCell {
                        it.apply {
                            addTitleText(pokemon.name)
                            addImage(IconCompat.createWithResource(context, pokemon.image), ListBuilder.SMALL_IMAGE)
                            addText(pokeType)
                            contentIntent = createPokemonIntent(pokemon.url)
                        }
                    }
                }
            }
        }

        return this
    }
}