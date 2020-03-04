import play.api.http.{ContentTypeOf, ContentTypes, Writeable}
import play.api.mvc.Codec
import scalatags.Text.all._

package object views {

  object implicits {
    implicit def contentTypeOfTag(implicit codec: Codec): ContentTypeOf[Tag] = {
      ContentTypeOf[Tag](Some(ContentTypes.HTML))
    }

    implicit def writeableOfTag(implicit codec: Codec): Writeable[Tag] = {
      Writeable(tag => codec.encode("<!DOCTYPE html>\n" + tag.render))
    }
  }

  object paths {
    object scripts {
      val main = "assets/client-fastopt.js"
    }

    object styles {
      object bootstrap{
        val min = "assets/stylesheets/bootstrap/bootstrap.min.css"

        val grid = "assets/stylesheets/bootstrap/bootstrap-grid.css"
        val gridMin = "assets/stylesheets/bootstrap/bootstrap-grid.min.css"
      }
    }

  }
}
