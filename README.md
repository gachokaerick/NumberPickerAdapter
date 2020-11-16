# NumberPickerAdapter
An adapter class that shows a list of selectable strings in an android recyclerview

Add maven { url 'https://jitpack.io' } to your project's build.gradle file
```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

Add the number picker dependency in app level build.gradle file
```groovy
dependencies {
    implementation 'com.github.EricGachoka:NumberPickerAdapter:1.0.0'
}
```

Initialize the **NumberPickerRecyclerAdapter** with required parameters and set the adapter to your recyclerview.

#### Adapter Parameters
| Parameter | Description | Required | Default Value |
| --------- |:-----------:|:---------:|-----------:|
| context   | activity context | true | |
| dataList | a list of strings to be displayed | true | |
| selectedItemDrawable | background drawable to use when item is selected | true | |
| unselectedItemDrawable | background drawable to use when item is deselected | true | |
| selectedItemTextColor | hex color code to use when item is selected e.g. #00ff00 | true | |
| unselectedItemTextColor | hex color code to use when item is deselected e.g. #ffff00 | true | |
| onItemClicked | callback function to be used when item is clicked | true | |
| tvWidth | width of textView in dp | false | 90 |
| tvHeight | height of textView in dp | false | 50 |
| tvMarginTop | top margin of textView in dp | false | 0 |
| tvMarginBottom | bottom margin of textView in dp | false | 0 |
| tvMarginLeft | left margin of textView in dp | false | 0 |
| tvMarginRight | right margin of textView in dp | false | 0 |
| tvMarginPaddingTop | top padding of textView in dp | false | 12 |
| tvMarginPaddingBottom | bottom padding of textView in dp | false | 12 |
| tvMarginPaddingLeft | left padding of textView in dp | false | 12 |
| tvMarginPaddingRight | right padding of textView in dp | false | 12 |
| tvTextSize | size of textView in sp | false | 16 |

#### Sample Usage
##### Activity
```kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var recyclerview: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerview = findViewById(R.id.rvNumbers)
        val adapter = NumberPickerRecyclerAdapter(
            this, getListOfNumbers(1, 50),
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.bg_black_card,
                null
            ),
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.bg_white_card,
                null
            ),
            "#ffffff",
            "#000000",
            { view, item -> numberSelected(item) },
            tvTextSize = 12
        )
        recyclerview.adapter = adapter
    }

    private fun getListOfNumbers(from: Int, to: Int, reversed: Boolean = false): List<String> {
        val list = mutableListOf<String>()
        for (num in from..to) {
            list.add("$num")
        }
        return if (reversed) {
            list.reversed()
        } else {
            list
        }
    }
    // callback function
    private fun numberSelected(item: String?) {
        if (item != null) {
            Toast.makeText(this, item, Toast.LENGTH_SHORT)
                .show()
        }
    }
}
```
##### bg_black_card
```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item>
        <shape android:shape="rectangle">
            <solid android:color="#000000" />
            <corners android:radius="24dp"/>
        </shape>
    </item>
</selector>
```
##### bg_white_card
```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item>
        <shape android:shape="rectangle">
            <solid android:color="#ffffff" />
            <corners android:radius="24dp"/>
        </shape>
    </item>
</selector>
```
##### activity_main
```xml
<?xml version="1.0" encoding="utf-8"?>

<androidx.recyclerview.widget.RecyclerView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/rvNumbers"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager" />
```

You can also view the implementation from the *NumberPickerAdapter/app/* folder
 