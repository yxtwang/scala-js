/* Scala.js compiler
 * Copyright 2013 LAMP/EPFL
 * @author  Sébastien Doeraene
 */

package scala.scalajs.compiler

import scala.tools.nsc._

/** Extension of ScalaPrimitives for primitives only relevant to the JS backend
 *
 *  @author Sébastie Doeraene
 */
abstract class JSPrimitives {
  val global: Global

  type ThisJSGlobalAddons = JSGlobalAddons {
    val global: JSPrimitives.this.global.type
  }

  val jsAddons: ThisJSGlobalAddons

  import global._
  import jsAddons._
  import definitions._
  import rootMirror._
  import jsDefinitions._
  import scalaPrimitives._

  // Conversions from Scala types to JS types
  val V2JS = 300 // Unit
  val Z2JS = 301 // Boolean
  //val C2JS = 302 // Char
  val N2JS = 303 // Number (any numeric type except for Long)
  val S2JS = 304 // String
  val F2JS = 305 // FunctionN
  val F2JSTHIS = 306 // ThisFunctionN

  // Conversions from JS types to Scala types
  val JS2Z = 311 // Boolean
  //val JS2C = 312 // Char
  val JS2N = 313 // Number (any numeric type)
  val JS2S = 314 // String

  val GETGLOBAL = 320 // Get the top-level object (`window` in browsers)
  val DYNNEW = 321    // Instantiate a new JavaScript object

  val DYNSELECT = 330 // js.Dynamic.selectDynamic
  val DYNUPDATE = 331 // js.Dynamic.updateDynamic
  val DYNAPPLY = 332  // js.Dynamic.applyDynamic
  val DYNLITN = 333   // js.Dynamic.literal.applyDynamicNamed
  val DYNLIT = 334    // js.Dynamic.literal.applyDynamic

  val DICT_DEL = 335   // js.Dictionary.delete
  val DICT_PROPS = 336 // js.Dictionary.propertiesOf

  val ARR_CREATE = 337 // js.Array.apply (array literal syntax)

  val RTJ2J = 338 // Runtime Long to Long
  val J2RTJ = 339 // Long to Runtime Long

  val NTR_MOD_SUFF  = 340 // scala.reflect.NameTransformer.MODULE_SUFFIX_STRING
  val NTR_NAME_JOIN = 341 // scala.relfect.NameTransformer.NAME_JOIN_STRING

  val TYPEOF = 342   // typeof x
  val DEBUGGER = 343 // js.debugger()
  val HASPROP = 344  // js.Object.hasProperty(o, p), equiv to `p in o` in JS

  /** Initialize the map of primitive methods */
  def init() {

    addPrimitive(JSAny_fromUnit, V2JS)
    addPrimitive(JSAny_fromBoolean, Z2JS)
    addPrimitive(JSAny_fromByte, N2JS)
    addPrimitive(JSAny_fromShort, N2JS)
    addPrimitive(JSAny_fromInt, N2JS)
    addPrimitive(JSAny_fromFloat, N2JS)
    addPrimitive(JSAny_fromDouble, N2JS)
    addPrimitive(JSAny_fromString, S2JS)

    for (i <- 0 to 22)
      addPrimitive(JSAny_fromFunction(i), F2JS)
    for (i <- 1 to 22)
      addPrimitive(JSThisFunction_fromFunction(i), F2JSTHIS)

    addPrimitive(JSBoolean_toBoolean, JS2Z)
    addPrimitive(JSNumber_toDouble, JS2N)
    addPrimitive(JSString_toScalaString, JS2S)

    addPrimitive(JSDynamic_global, GETGLOBAL)
    addPrimitive(JSDynamic_newInstance, DYNNEW)

    addPrimitive(JSDynamic_selectDynamic, DYNSELECT)
    addPrimitive(JSDynamic_updateDynamic, DYNUPDATE)
    addPrimitive(JSDynamic_applyDynamic, DYNAPPLY)
    addPrimitive(JSDynamicLiteral_applyDynamicNamed, DYNLITN)
    addPrimitive(JSDynamicLiteral_applyDynamic, DYNLIT)

    addPrimitive(JSDictionary_delete, DICT_DEL)
    addPrimitive(JSDictionary_propertiesOf, DICT_PROPS)

    addPrimitive(JSArray_create, ARR_CREATE)

    addPrimitive(RuntimeLong_from, RTJ2J)
    addPrimitive(RuntimeLong_to, J2RTJ)

    val ntModule = getRequiredModule("scala.reflect.NameTransformer")

    addPrimitive(getMember(ntModule, newTermName("MODULE_SUFFIX_STRING")), NTR_MOD_SUFF)
    addPrimitive(getMember(ntModule, newTermName("NAME_JOIN_STRING")), NTR_NAME_JOIN)

    addPrimitive(JSPackage_typeOf, TYPEOF)
    addPrimitive(JSPackage_debugger, DEBUGGER)

    addPrimitive(JSObject_hasProperty, HASPROP)
  }

  def isJavaScriptPrimitive(code: Int) =
    code >= 300 && code < 350
}
