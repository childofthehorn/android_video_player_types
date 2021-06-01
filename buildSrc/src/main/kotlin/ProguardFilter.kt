import java.io.File
import java.io.FilenameFilter

class ProguardFilter : FilenameFilter {
    override fun accept(
        f: File,
        filename: String
    ): Boolean {
        //Support both standard proguard file type assignments
        return filename.endsWith("pro") || filename.endsWith("txt")
    }
}