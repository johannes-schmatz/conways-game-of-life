javadoc="/c/Program Files/Eclipse Adoptium/jdk-17.0.1.12-hotspot/bin/javadoc.exe"
lib="$HOME/Downloads/lwjgl-release-3.3.0-custom"

junit1="`ls $HOME/.p2/pool/plugins/org.junit_4.13.2.*`"
junit2="`ls $HOME/.p2/pool/plugins/org.hamcrest.*`"

cp=`find $lib -type f -regex ".*\.jar\$" -not -regex ".*-\(sources\|javadoc\).jar" -printf %p\;`

title="conways game of live"
packagenames="local.pixy.conwaysgame"
outdir="doc"

"$javadoc" \
	--module-path="$lib:$junit1:$junit2" \
	--class-path="$cp" \
	--source-path "./src:$lib" \
	-windowtitle \
	-doctitle="$title" \
	-splitindex \
	-use \
	-version \
	-source 17 \
	-private \
	--show-members private \
	--show-types private \
	-locale de_DE \
	-html5 \
	-charset utf-8 \
	-author \
	-d "$outdir" \
	-subpackages $packagenames $@
#	-verbose \
#	--module-source-path "$lib" \
#	--add-modules="$junit0" \
#sourcefiles="src/module-info.java"
