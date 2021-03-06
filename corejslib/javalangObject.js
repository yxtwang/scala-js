/* Scala.js runtime support
 * Copyright 2013 LAMP/EPFL
 * Author: Sébastien Doeraene
 */

/* ------------------
 * java.lang.Object
 * ------------------ */

/** @constructor */
ScalaJS.c.java_lang_Object = function() {
};

/** @constructor */
ScalaJS.inheritable.java_lang_Object = function() {};
ScalaJS.inheritable.java_lang_Object.prototype =
  ScalaJS.c.java_lang_Object.prototype;

ScalaJS.c.java_lang_Object.prototype.init___ = function() {
  return this;
}

ScalaJS.c.java_lang_Object.prototype.getClass__Ljava_lang_Class = function() {
  return this.$classData.getClassOf();
}

ScalaJS.c.java_lang_Object.prototype.hashCode__I = function() {
  // TODO
  return 42;
}

ScalaJS.c.java_lang_Object.prototype.equals__O__Z = function(rhs) {
  return this === rhs;
}

ScalaJS.c.java_lang_Object.prototype.clone__O = function() {
  if (ScalaJS.is.java_lang_Cloneable(this)) {
    function Clone(from) {
      for (var field in from)
        if (from["hasOwnProperty"](field))
          this[field] = from[field];
    }
    Clone.prototype = ScalaJS.g["Object"]["getPrototypeOf"](this);
    return new Clone(this);
  } else {
    throw new ScalaJS.c.java_lang_CloneNotSupportedException().init___();
  }
}

ScalaJS.c.java_lang_Object.prototype.toString__T = function() {
  // getClass().getName() + "@" + Integer.toHexString(hashCode())
  var className = this.getClass__Ljava_lang_Class().getName__T();
  var hashCode = this.hashCode__I();
  return className + '@' + hashCode.toString(16);
}

// JSExport for toString(). We always need to export this, since we
// rely on JS calling it automatically when we do things like:
// `"" + obj`
ScalaJS.c.java_lang_Object.prototype.toString = function() {
  return this.toString__T();
}

ScalaJS.c.java_lang_Object.prototype.notify__V = function() {}
ScalaJS.c.java_lang_Object.prototype.notifyAll__V = function() {}
ScalaJS.c.java_lang_Object.prototype.wait__J__V = function() {}
ScalaJS.c.java_lang_Object.prototype.wait__J__I__V = function() {}
ScalaJS.c.java_lang_Object.prototype.wait__V = function() {}

ScalaJS.c.java_lang_Object.prototype.finalize__V = function() {}

// Instance tests

ScalaJS.is.java_lang_Object = function(obj) {
  return !!((obj && obj.$classData &&
    obj.$classData.ancestors.java_lang_Object) ||
    (typeof(obj) === "string"));
};

ScalaJS.as.java_lang_Object = function(obj) {
  if (ScalaJS.is.java_lang_Object(obj) || obj === null)
    return obj;
  else
    ScalaJS.throwClassCastException(obj, "java.lang.Object");
};

ScalaJS.isArrayOf.java_lang_Object = (function(obj, depth) {
  var data = obj && obj.$classData;
  if (!data)
    return false;
  var arrayDepth = data.arrayDepth || 0;

  if (arrayDepth < depth)
    return false; // because Array[A] </: Array[Array[A]]
  else if (arrayDepth > depth)
    return true; // because Array[Array[A]] <: Array[Object]
  else
    return !data.arrayBase.isPrimitive; // because Array[Int] </: Array[Object]
});

ScalaJS.asArrayOf.java_lang_Object = (function(obj, depth) {
  if ((ScalaJS.isArrayOf.java_lang_Object(obj, depth) || (obj === null))) {
    return obj
  } else {
    ScalaJS.throwArrayCastException(obj, "Ljava.lang.Object;", depth)
  }
});

// Data

ScalaJS.data.java_lang_Object =
  new ScalaJS.ClassTypeData(
    {java_lang_Object:0},
    false, "java.lang.Object", null,
    {java_lang_Object: 1},
    ScalaJS.is.java_lang_Object,
    ScalaJS.isArrayOf.java_lang_Object);

ScalaJS.c.java_lang_Object.prototype.$classData =
  ScalaJS.data.java_lang_Object;
