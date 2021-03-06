/* Scala.js compiler
 * Copyright 2013 LAMP/EPFL
 * @author  Sébastien Doeraene
 */

package scala.scalajs.compiler

import scala.tools.nsc._
import scala.tools.nsc.plugins.{
  Plugin => NscPlugin, PluginComponent => NscPluginComponent
}
import scala.collection.{ mutable, immutable }

/** Main entry point for the Scala.js compiler plugin
 *
 *  @author Sébastien Doeraene
 */
class ScalaJSPlugin(val global: Global) extends NscPlugin {
  import global._

  val name = "scalajs"
  val description = "Compile to JavaScript"
  val components = List[NscPluginComponent](PrepInteropComponent, GenCodeComponent)

  /** Addons for JavaScript platform */
  object jsAddons extends {
    val global: ScalaJSPlugin.this.global.type = ScalaJSPlugin.this.global
  } with JSGlobalAddons with Compat210Component

  object scalaJSOpts extends ScalaJSOptions {
    var fixClassOf: Boolean = false
  }

  object PrepInteropComponent extends {
    val global: ScalaJSPlugin.this.global.type = ScalaJSPlugin.this.global
    val jsAddons: ScalaJSPlugin.this.jsAddons.type = ScalaJSPlugin.this.jsAddons
    val scalaJSOpts = ScalaJSPlugin.this.scalaJSOpts
    override val runsAfter = List("typer")
    override val runsBefore = List("pickle")
  } with PrepJSInterop

  object GenCodeComponent extends {
    val global: ScalaJSPlugin.this.global.type = ScalaJSPlugin.this.global
    val jsAddons: ScalaJSPlugin.this.jsAddons.type = ScalaJSPlugin.this.jsAddons
    override val runsAfter = List("mixin")
    override val runsBefore = List("delambdafy", "cleanup", "terminal")
  } with GenJSCode

  override def processOptions(options: List[String],
      error: String => Unit): Unit = {
    for (option <- options) {
      if (option == "fixClassOf") {
        scalaJSOpts.fixClassOf = true
      } else {
        error("Option not understood: " + option)
      }
    }
  }

  override val optionsHelp: Option[String] = Some(s"""
      |  -P:$name:fixClassOf          repair calls to Predef.classOf that reach ScalaJS
      |     WARNING: This is a tremendous hack! Expect ugly errors if you use this option.
      """.stripMargin)

}
