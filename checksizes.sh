#!/bin/sh

FULLVER="$1"

case $FULLVER in
  2.10.2)
    VER=2.10
    ;;
  2.10.3)
    VER=2.10
    ;;
  2.10.4-RC3)
    VER=2.10
    ;;
  2.11.0-M7)
    VER=2.11.0-M7
    ;;
  2.11.0-M8)
    VER=2.11.0-M8
    ;;
esac

REVERSI_EXTDEPS_SIZE=$(stat '-c%s' examples/reversi/target/scala-$VER/reversi-extdeps.js)
REVERSI_SELF_SIZE=$(stat '-c%s' examples/reversi/target/scala-$VER/reversi.js)
REVERSI_PREOPT_SIZE=$(stat '-c%s' examples/reversi/target/scala-$VER/reversi-preopt.js)
REVERSI_OPT_SIZE=$(stat '-c%s' examples/reversi/target/scala-$VER/reversi-opt.js)

case $FULLVER in
  2.10.2)
    REVERSI_EXTDEPS_EXPECTEDSIZE=19000000
    REVERSI_SELF_EXPECTEDSIZE=65000
    REVERSI_PREOPT_EXPECTEDSIZE=1600000
    REVERSI_OPT_EXPECTEDSIZE=195000
    ;;
  2.10.3)
    REVERSI_EXTDEPS_EXPECTEDSIZE=19000000
    REVERSI_SELF_EXPECTEDSIZE=65000
    REVERSI_PREOPT_EXPECTEDSIZE=1600000
    REVERSI_OPT_EXPECTEDSIZE=195000
    ;;
  2.10.4-RC3)
    REVERSI_EXTDEPS_EXPECTEDSIZE=19000000
    REVERSI_SELF_EXPECTEDSIZE=65000
    REVERSI_PREOPT_EXPECTEDSIZE=1600000
    REVERSI_OPT_EXPECTEDSIZE=195000
    ;;
  2.11.0-M7)
    REVERSI_EXTDEPS_EXPECTEDSIZE=18000000
    REVERSI_SELF_EXPECTEDSIZE=65000
    REVERSI_PREOPT_EXPECTEDSIZE=1550000
    REVERSI_OPT_EXPECTEDSIZE=190000
    ;;
  2.11.0-M8)
    REVERSI_EXTDEPS_EXPECTEDSIZE=17000000
    REVERSI_SELF_EXPECTEDSIZE=61000
    REVERSI_PREOPT_EXPECTEDSIZE=1550000
    REVERSI_OPT_EXPECTEDSIZE=190000
    ;;
esac

echo "Reversi extdeps size = $REVERSI_EXTDEPS_SIZE (expected $REVERSI_EXTDEPS_EXPECTEDSIZE)"
echo "Reversi self size = $REVERSI_SELF_SIZE (expected $REVERSI_SELF_EXPECTEDSIZE)"
echo "Reversi preopt size = $REVERSI_PREOPT_SIZE (expected $REVERSI_PREOPT_EXPECTEDSIZE)"
echo "Reversi opt size = $REVERSI_OPT_SIZE (expected $REVERSI_OPT_EXPECTEDSIZE)"

[ "$REVERSI_EXTDEPS_SIZE" -le "$REVERSI_EXTDEPS_EXPECTEDSIZE" ] && \
  [ "$REVERSI_SELF_SIZE" -le "$REVERSI_SELF_EXPECTEDSIZE" ] && \
  [ "$REVERSI_PREOPT_SIZE" -le "$REVERSI_PREOPT_EXPECTEDSIZE" ] && \
  [ "$REVERSI_OPT_SIZE" -le "$REVERSI_OPT_EXPECTEDSIZE" ]
