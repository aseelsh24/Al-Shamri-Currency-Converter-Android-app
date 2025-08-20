# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\user\AppData\Local\Android\Sdk\tools\proguard\proguard-android-optimize.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If you use reflection to access classes in your project, add the
# appropriate -keep rules here.
# Be sure to include the fully qualified name of the class in your keep
# rule; for example:
# -keep class com.example.MyClass
#
# If you want to serialize classes with something like GSON, you also
# need to keep the default constructor and fields.
# -keep class com.example.MyClass {
#   <init>();
#   <fields>;
# }

# For Room
-keep class * extends androidx.room.RoomDatabase
-keep class androidx.room.paging.LimitOffsetDataSource
-keep class * extends androidx.room.Entity
-keep @androidx.room.Entity class * { *; }
-keep class * extends androidx.room.Dao
-keep @androidx.room.Dao class * { *; }
-keep class * extends androidx.room.TypeConverter
-keep @androidx.room.TypeConverter class * { *; }
-keep class * extends androidx.lifecycle.ViewModel
-keep class * extends androidx.lifecycle.AndroidViewModel
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}
