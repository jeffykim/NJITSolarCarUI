import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.Toolkit;

/**
 * A complete Java class that demonstrates how to create an HTML viewer with styles,
 * using the JEditorPane, HTMLEditorKit, StyleSheet, and JFrame.
 * 
 * @author alvin alexander, devdaily.com.
 *
 */
public class HTMLContent
{
  public static void main(String[] args)
  {
    new HTMLContent();
  }
  
  public HTMLContent()
  {
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        // create jeditorpane
        JEditorPane jEditorPane = new JEditorPane();
        
        // make it read-only
        jEditorPane.setEditable(false);
        
        // create a scrollpane; modify its attributes as desired
        //JScrollPane scrollPane = new JScrollPane(jEditorPane); 
        
        // add an html editor kit
        HTMLEditorKit kit = new HTMLEditorKit();
        jEditorPane.setEditorKit(kit);
        
        // add some styles to the html
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule("body{\n" + 
        		"	background-color:black;\n" + 
        		"    color:black;\n" + 
        		"}");
        styleSheet.addRule("#full_panel{\n" + 
        		"	background-color:rgba(138,138,138,1.00);\n" + 
        		"	border-color:darkslategrey;\n" + 
        		"  padding-bottom: 0px;\n" + 
        		"   width: 783px;\n" + 
        		"   height: 457px;\n" + 
        		"   border-style: solid; \n" + 
        		"   border-right-width: 3px;\n" + 
        		"   border-left-width: 1px;\n" + 
        		"   border-top-width:3px;\n" + 
        		"	\n" + 
        		"   -webkit-border-radius: 10px;\n" + 
        		" 	/*box-shadow: 10px 10px #888888*/\n" + 
        		"}");
        styleSheet.addRule("h3{\n" + 
        		"	font-size: 18px;\n" + 
        		"}");
        styleSheet.addRule(".panel_one_title{\n" + 
        		"	text-align: center;\n" + 
        		"	font-size: 20px;\n" + 
        		"	\n" + 
        		"}");
        styleSheet.addRule("#left_panel{\n" + 
        		"	color:red;\n" + 
        		"border-color: blue;\n" + 
        		"  width: 400px;\n" + 
        		"  height: 220px;\n" + 
        		" \n" + 
        		"   border-style: solid; \n" + 
        		"   border-width: 3px;\n" + 
        		"   border-top-width: 1.5px;\n" + 
        		"    border-right-width: 2px;\n" + 
        		"	border-bottom-width: 2px;\n" + 
        		"   -webkit-border-radius: 6px;\n" + 
        		"	font-family: Segoe, Segoe UI, DejaVu Sans, Trebuchet MS, Verdana,\" sans-serif\";\n" + 
        		"	font-size: 14px;\n" + 
        		"   \n" + 
        		"}");
        styleSheet.addRule("#left_panel2{\n" + 
        		"  width: 400px;\n" + 
        		"  height: 232px;\n" + 
        		"  border-color:rgba(192,0,3,1.00);\n" + 
        		"  color:red;\n" + 
        		"   border-style: solid; \n" + 
        		"   border-width: 3px;\n" + 
        		"   border-top-width: 2px;\n" + 
        		"   border-bottom-width: 2px;\n" + 
        		"   border-right-width: 2px;\n" + 
        		"   -webkit-border-radius: 6px;\n" + 
        		"	font-family: Segoe, Segoe UI, DejaVu Sans, Trebuchet MS, Verdana,\" sans-serif\";\n" + 
        		"}");
        styleSheet.addRule("#additional_data{\n" + 
        		"	position: relative;\n" + 
        		"	margin-left: 160px;\n" + 
        		"	bottom: 185px;\n" + 
        		"	font-weight: bold;\n" + 
        		"	\n" + 
        		"}");
        styleSheet.addRule("#right_panel{\n" + 
        		"position: relative;\n" + 
        		"left: 405px;\n" + 
        		"bottom: 460px;\n" + 
        		"width: 376px;\n" + 
        		"height: 458px;\n" + 
        		"border-color: black; \n" + 
        		"border-style: solid; \n" + 
        		"border-width: 2px;\n" + 
        		"border-bottom-width: 1px;\n" + 
        		"border-left-width: 1px;\n" + 
        		"border-right-width: 1px;\n" + 
        		"-webkit-border-radius: 6px;\n" + 
        		"}");
        styleSheet.addRule("#dot{\n" + 
        		"	position: absolute;\n" + 
        		"	height: 7px;\n" + 
        		"	width: 7px;\n" + 
        		"	outline: 1px solid black;\n" + 
        		"	background-color: red;\n" + 
        		"}");

        // create some simple html as a string
        String htmlString = "<html>\n" + 
        		"<head>\n" + 
        		"<meta charset=\"utf-8\">\n" + 
        		 
        		"\n" + 
        		"\n" + 
        		"\n" + 
        		"\n" + 
        		"<script src=\"Button_Function.js\"></script>\n" + 
        		"\n" + 
        		"<title>Solar Car UI Design X</title>\n" + 
        		"\n" + 
        		"</head>\n" + 
        		"<body>\n" + 
        		"\n" + 
        		"<div id=\"full_panel\">\n" + 
        		"\n" + 
        		"\n" + 
        		"<div id=\"left_panel\">\n" + 
        		"	<div class=\"panel_one_title\"><b>Telemetary Data</b></div>\n" + 
        		"	<ul><b>\n" + 
        		"		<li> Battery Temp: </li>\n" + 
        		"		<br>\n" + 
        		"		<li> Battery Volt:</li>\n" + 
        		"		<br>\n" + 
        		"		<li> Battey Current: </li>\n" + 
        		"		<br>\n" + 
        		"		<li> Solar Panel Volt: </li>\n" + 
        		"		<br>\n" + 
        		"		<li> Solar Panel Current:  </li>\n" + 
        		"		\n" + 
        		"		</b>	\n" + 
        		"	</ul>\n" + 
        		"	\n" + 
        		"	<div id=\"additional_data\">\n" + 
        		"	<ul>\n" + 
        		"	<li> Miles Remaining:</li>\n" + 
        		"	<br>\n" + 
        		"	<li>Miles Driven: <li>\n" + 
        		"	\n" + 
        		"	<li>Avg: km/h</li>\n" + 
        		"	</ul>\n" + 
        		"	</div>\n" + 
        		"	\n" + 
        		"	\n" + 
        		"	\n" + 
        		"	</div>\n" + 
        		"	\n" + 
        		"		<!---  Add range in miles based on average speed and current power and battery remaing percent ( also add solar panel volt then subtract the \"use\" )\n" + 
        		"		\n" + 
        		"		  -->\n" + 
        		"\n" + 
        		"\n" + 
        		"\n" + 
        		"<div id=\"left_panel2\">\n" + 
        		"  \n" + 
        		"  <div class=\"panel_one_title\"><strong>Speedometer</strong></div>\n" + 
        		"<script >(function () {\n" + 
        		"    \"use strict\";\n" + 
        		"    // this function is strict...\n" + 
        		"}());\n" + 
        		"var iCurrentSpeed = 0,\n" + 
        		"	iTargetSpeed = 0,\n" + 
        		"	bDecrement = null,\n" + 
        		"	job = null;\n" + 
        		"function degToRad(angle) {\n" + 
        		"	// Degrees to radians\n" + 
        		"	return ((angle * Math.PI) / 180);\n" + 
        		"}\n" + 
        		"function radToDeg(angle) {\n" + 
        		"	// Radians to degree\n" + 
        		"	return ((angle * 180) / Math.PI);\n" + 
        		"}\n" + 
        		"function drawLine(options, line) {\n" + 
        		"	// Draw a line using the line object passed in\n" + 
        		"	options.ctx.beginPath();\n" + 
        		"	// Set attributes of open\n" + 
        		"	options.ctx.globalAlpha = line.alpha;\n" + 
        		"	options.ctx.lineWidth = line.lineWidth;\n" + 
        		"	options.ctx.fillStyle = line.fillStyle;\n" + 
        		"	options.ctx.strokeStyle = line.fillStyle;\n" + 
        		"	options.ctx.moveTo(line.from.X,\n" + 
        		"		line.from.Y);\n" + 
        		"	// Plot the line\n" + 
        		"	options.ctx.lineTo(\n" + 
        		"		line.to.X,\n" + 
        		"		line.to.Y\n" + 
        		"	);\n" + 
        		"	options.ctx.stroke();\n" + 
        		"}\n" + 
        		"function createLine(fromX, fromY, toX, toY, fillStyle, lineWidth, alpha) {\n" + 
        		"	// Create a line object using Javascript object notation\n" + 
        		"	return {\n" + 
        		"		from: {\n" + 
        		"			X: fromX,\n" + 
        		"			Y: fromY\n" + 
        		"		},\n" + 
        		"		to:	{\n" + 
        		"			X: toX,\n" + 
        		"			Y: toY\n" + 
        		"		},\n" + 
        		"		fillStyle: fillStyle,\n" + 
        		"		lineWidth: lineWidth,\n" + 
        		"		alpha: alpha\n" + 
        		"	};\n" + 
        		"}\n" + 
        		"function drawOuterMetallicArc(options) {\n" + 
        		"	/* Draw the metallic border of the speedometer \n" + 
        		"	 * Outer grey area\n" + 
        		"	 */\n" + 
        		"	options.ctx.beginPath();\n" + 
        		"	// Nice shade of grey\n" + 
        		"	options.ctx.fillStyle = \"rgb(127,127,127)\";\n" + 
        		"	// Draw the outer circle\n" + 
        		"	options.ctx.arc(options.center.X,\n" + 
        		"		options.center.Y,\n" + 
        		"		options.radius,\n" + 
        		"		0,\n" + 
        		"		Math.PI,\n" + 
        		"		true);\n" + 
        		"	// Fill the last object\n" + 
        		"	options.ctx.fill();\n" + 
        		"}\n" + 
        		"function drawInnerMetallicArc(options) {\n" + 
        		"	/* Draw the metallic border of the speedometer \n" + 
        		"	 * Inner white area\n" + 
        		"	 */\n" + 
        		"	options.ctx.beginPath();\n" + 
        		"	// White\n" + 
        		"	options.ctx.fillStyle = \"rgb(255,255,255)\";\n" + 
        		"	// Outer circle (subtle edge in the grey)\n" + 
        		"	options.ctx.arc(options.center.X,\n" + 
        		"					options.center.Y,\n" + 
        		"					(options.radius / 100) * 90,\n" + 
        		"					0,\n" + 
        		"					Math.PI,\n" + 
        		"					true);\n" + 
        		"	options.ctx.fill();\n" + 
        		"}\n" + 
        		"function drawMetallicArc(options) {\n" + 
        		"	/* Draw the metallic border of the speedometer\n" + 
        		"	 * by drawing two semi-circles, one over lapping\n" + 
        		"	 * the other with a bot of alpha transparency\n" + 
        		"	 */\n" + 
        		"	drawOuterMetallicArc(options);\n" + 
        		"	drawInnerMetallicArc(options);\n" + 
        		"}\n" + 
        		"function drawBackground(options) {\n" + 
        		"	/* Black background with alphs transparency to\n" + 
        		"	 * blend the edges of the metallic edge and\n" + 
        		"	 * black background\n" + 
        		"	 */\n" + 
        		"    var i = 0;\n" + 
        		"	options.ctx.globalAlpha = 0.2;\n" + 
        		"	options.ctx.fillStyle = \"rgb(0,0,0)\";\n" + 
        		"	// Draw semi-transparent circles\n" + 
        		"	for (i = 170; i < 180; i++) {\n" + 
        		"		options.ctx.beginPath();\n" + 
        		"		options.ctx.arc(options.center.X,\n" + 
        		"			options.center.Y,\n" + 
        		"			i,\n" + 
        		"			0,\n" + 
        		"			Math.PI,\n" + 
        		"			true);\n" + 
        		"		options.ctx.fill();\n" + 
        		"	}\n" + 
        		"}\n" + 
        		"function applyDefaultContextSettings(options) {\n" + 
        		"	/* Helper function to revert to gauges\n" + 
        		"	 * default settings\n" + 
        		"	 */\n" + 
        		"	options.ctx.lineWidth = 2;\n" + 
        		"	options.ctx.globalAlpha = 0.5;\n" + 
        		"	options.ctx.strokeStyle = \"rgb(255, 255, 255)\";\n" + 
        		"	options.ctx.fillStyle = 'rgb(255,255,255)';\n" + 
        		"}\n" + 
        		"function drawSmallTickMarks(options) {\n" + 
        		"	/* The small tick marks against the coloured\n" + 
        		"	 * arc drawn every 5 mph from 10 degrees to\n" + 
        		"	 * 170 degrees.\n" + 
        		"	 */\n" + 
        		"	var tickvalue = options.levelRadius - 8,\n" + 
        		"	    iTick = 0,\n" + 
        		"	    gaugeOptions = options.gaugeOptions,\n" + 
        		"	    iTickRad = 0,\n" + 
        		"	    onArchX,\n" + 
        		"	    onArchY,\n" + 
        		"	    innerTickX,\n" + 
        		"	    innerTickY,\n" + 
        		"	    fromX,\n" + 
        		"	    fromY,\n" + 
        		"	    line,\n" + 
        		"		toX,\n" + 
        		"		toY;\n" + 
        		"	applyDefaultContextSettings(options);\n" + 
        		"	// Tick every 20 degrees (small ticks)\n" + 
        		"	for (iTick = 10; iTick < 180; iTick += 20) {\n" + 
        		"		iTickRad = degToRad(iTick);\n" + 
        		"		/* Calculate the X and Y of both ends of the\n" + 
        		"		 * line I need to draw at angle represented at Tick.\n" + 
        		"		 * The aim is to draw the a line starting on the \n" + 
        		"		 * coloured arc and continueing towards the outer edge\n" + 
        		"		 * in the direction from the center of the gauge. \n" + 
        		"		 */\n" + 
        		"		onArchX = gaugeOptions.radius - (Math.cos(iTickRad) * tickvalue);\n" + 
        		"		onArchY = gaugeOptions.radius - (Math.sin(iTickRad) * tickvalue);\n" + 
        		"		innerTickX = gaugeOptions.radius - (Math.cos(iTickRad) * gaugeOptions.radius);\n" + 
        		"		innerTickY = gaugeOptions.radius - (Math.sin(iTickRad) * gaugeOptions.radius);\n" + 
        		"		fromX = (options.center.X - gaugeOptions.radius) + onArchX;\n" + 
        		"		fromY = (gaugeOptions.center.Y - gaugeOptions.radius) + onArchY;\n" + 
        		"		toX = (options.center.X - gaugeOptions.radius) + innerTickX;\n" + 
        		"		toY = (gaugeOptions.center.Y - gaugeOptions.radius) + innerTickY;\n" + 
        		"		// Create a line expressed in JSON\n" + 
        		"		line = createLine(fromX, fromY, toX, toY, \"rgb(127,127,127)\", 3, 0.6);\n" + 
        		"		// Draw the line\n" + 
        		"		drawLine(options, line);\n" + 
        		"	}\n" + 
        		"}\n" + 
        		"function drawLargeTickMarks(options) {\n" + 
        		"	/* The large tick marks against the coloured\n" + 
        		"	 * arc drawn every 10 mph from 10 degrees to\n" + 
        		"	 * 170 degrees.\n" + 
        		"	 */\n" + 
        		"	var tickvalue = options.levelRadius - 8,\n" + 
        		"	    iTick = 0,\n" + 
        		"        gaugeOptions = options.gaugeOptions,\n" + 
        		"        iTickRad = 0,\n" + 
        		"        innerTickY,\n" + 
        		"        innerTickX,\n" + 
        		"        onArchX,\n" + 
        		"        onArchY,\n" + 
        		"        fromX,\n" + 
        		"        fromY,\n" + 
        		"        toX,\n" + 
        		"        toY,\n" + 
        		"        line;\n" + 
        		"	applyDefaultContextSettings(options);\n" + 
        		"	tickvalue = options.levelRadius - 2;\n" + 
        		"	// 10 units (major ticks)\n" + 
        		"	for (iTick = 20; iTick < 180; iTick += 20) {\n" + 
        		"		iTickRad = degToRad(iTick);\n" + 
        		"		/* Calculate the X and Y of both ends of the\n" + 
        		"		 * line I need to draw at angle represented at Tick.\n" + 
        		"		 * The aim is to draw the a line starting on the \n" + 
        		"		 * coloured arc and continueing towards the outer edge\n" + 
        		"		 * in the direction from the center of the gauge. \n" + 
        		"		 */\n" + 
        		"		onArchX = gaugeOptions.radius - (Math.cos(iTickRad) * tickvalue);\n" + 
        		"		onArchY = gaugeOptions.radius - (Math.sin(iTickRad) * tickvalue);\n" + 
        		"		innerTickX = gaugeOptions.radius - (Math.cos(iTickRad) * gaugeOptions.radius);\n" + 
        		"		innerTickY = gaugeOptions.radius - (Math.sin(iTickRad) * gaugeOptions.radius);\n" + 
        		"		fromX = (options.center.X - gaugeOptions.radius) + onArchX;\n" + 
        		"		fromY = (gaugeOptions.center.Y - gaugeOptions.radius) + onArchY;\n" + 
        		"		toX = (options.center.X - gaugeOptions.radius) + innerTickX;\n" + 
        		"		toY = (gaugeOptions.center.Y - gaugeOptions.radius) + innerTickY;\n" + 
        		"		// Create a line expressed in JSON\n" + 
        		"		line = createLine(fromX, fromY, toX, toY, \"rgb(127,127,127)\", 3, 0.6);\n" + 
        		"		// Draw the line\n" + 
        		"		drawLine(options, line);\n" + 
        		"	}\n" + 
        		"}\n" + 
        		"function drawTicks(options) {\n" + 
        		"	/* Two tick in the coloured arc!\n" + 
        		"	 * Small ticks every 5\n" + 
        		"	 * Large ticks every 10\n" + 
        		"	 */\n" + 
        		"	drawSmallTickMarks(options);\n" + 
        		"	drawLargeTickMarks(options);\n" + 
        		"}\n" + 
        		"function drawTextMarkers(options) {\n" + 
        		"	/* The text labels marks above the coloured\n" + 
        		"	 * arc drawn every 10 mph from 10 degrees to\n" + 
        		"	 * 170 degrees.\n" + 
        		"	 */\n" + 
        		"	var innerTickX = 0,\n" + 
        		"	    innerTickY = 0,\n" + 
        		"        iTick = 0,\n" + 
        		"        gaugeOptions = options.gaugeOptions,\n" + 
        		"        iTickToPrint = 0;//check\n" + 
        		"	applyDefaultContextSettings(options);\n" + 
        		"	// Font styling\n" + 
        		"	options.ctx.font = 'italic 10px sans-serif';\n" + 
        		"	options.ctx.textBaseline = 'top';\n" + 
        		"	options.ctx.beginPath();\n" + 
        		"	// Tick every 20 (small ticks)\n" + 
        		"	for (iTick = 10; iTick < 180; iTick += 20) {\n" + 
        		"		innerTickX = gaugeOptions.radius - (Math.cos(degToRad(iTick)) * gaugeOptions.radius);\n" + 
        		"		innerTickY = gaugeOptions.radius - (Math.sin(degToRad(iTick)) * gaugeOptions.radius);\n" + 
        		"		// Some cludging to center the values (TODO: Improve)\n" + 
        		"		if (iTick <= 10) {\n" + 
        		"			options.ctx.fillText(iTickToPrint, (options.center.X - gaugeOptions.radius - 12) + innerTickX,\n" + 
        		"					(gaugeOptions.center.Y - gaugeOptions.radius - 12) + innerTickY + 5);\n" + 
        		"		} else if (iTick < 50) {\n" + 
        		"			options.ctx.fillText(iTickToPrint, (options.center.X - gaugeOptions.radius - 12) + innerTickX - 5,\n" + 
        		"					(gaugeOptions.center.Y - gaugeOptions.radius - 12) + innerTickY + 5);\n" + 
        		"		} else if (iTick < 90) {\n" + 
        		"			options.ctx.fillText(iTickToPrint, (options.center.X - gaugeOptions.radius - 12) + innerTickX,\n" + 
        		"					(gaugeOptions.center.Y - gaugeOptions.radius - 12) + innerTickY);\n" + 
        		"		} else if (iTick === 90) {\n" + 
        		"			options.ctx.fillText(iTickToPrint, (options.center.X - gaugeOptions.radius - 12) + innerTickX + 4,\n" + 
        		"					(gaugeOptions.center.Y - gaugeOptions.radius - 12) + innerTickY);\n" + 
        		"		} else if (iTick < 145) {\n" + 
        		"			options.ctx.fillText(iTickToPrint, (options.center.X - gaugeOptions.radius - 12) + innerTickX + 10,\n" + 
        		"					(gaugeOptions.center.Y - gaugeOptions.radius - 12) + innerTickY);\n" + 
        		"		} else {\n" + 
        		"			options.ctx.fillText(iTickToPrint, (options.center.X - gaugeOptions.radius - 12) + innerTickX + 15,\n" + 
        		"					(gaugeOptions.center.Y - gaugeOptions.radius - 12) + innerTickY + 5);\n" + 
        		"		}\n" + 
        		"		// MPH increase by 10 every 20 degrees\n" + 
        		"		iTickToPrint += Math.round(90 / 9);\n" + 
        		"	}\n" + 
        		"    options.ctx.stroke();\n" + 
        		"}\n" + 
        		"function drawSpeedometerPart(options, alphaValue, strokeStyle, startPos) {\n" + 
        		"	/* Draw part of the arc that represents\n" + 
        		"	* the colour speedometer arc\n" + 
        		"	*/\n" + 
        		"	options.ctx.beginPath();\n" + 
        		"	options.ctx.globalAlpha = alphaValue;\n" + 
        		"	options.ctx.lineWidth = 5;\n" + 
        		"	options.ctx.strokeStyle = strokeStyle;\n" + 
        		"	options.ctx.arc(options.center.X,\n" + 
        		"		options.center.Y,\n" + 
        		"		options.levelRadius,\n" + 
        		"		Math.PI + (Math.PI / 360 * startPos),\n" + 
        		"		0 - (Math.PI / 360 * 10),\n" + 
        		"		false);\n" + 
        		"	options.ctx.stroke();\n" + 
        		"}\n" + 
        		"function drawSpeedometerColourArc(options) {\n" + 
        		"	/* Draws the colour arc.  Three different colours\n" + 
        		"	 * used here; thus, same arc drawn 3 times with\n" + 
        		"	 * different colours.\n" + 
        		"	 * TODO: Gradient possible?\n" + 
        		"	 */\n" + 
        		"	var startOfGreen = 10,\n" + 
        		"	    endOfGreen = 200,\n" + 
        		"	    endOfOrange = 280;\n" + 
        		"	drawSpeedometerPart(options, 1.0, \"rgb(82, 240, 55)\", startOfGreen);\n" + 
        		"	drawSpeedometerPart(options, 0.9, \"rgb(198, 111, 0)\", endOfGreen);\n" + 
        		"	drawSpeedometerPart(options, 0.9, \"rgb(255, 0, 0)\", endOfOrange);\n" + 
        		"}\n" + 
        		"function drawNeedleDial(options, alphaValue, strokeStyle, fillStyle) {\n" + 
        		"	/* Draws the metallic dial that covers the base of the\n" + 
        		"	* needle.\n" + 
        		"	*/\n" + 
        		"    var i = 0;\n" + 
        		"	options.ctx.globalAlpha = alphaValue;\n" + 
        		"	options.ctx.lineWidth = 3;\n" + 
        		"	options.ctx.strokeStyle = strokeStyle;\n" + 
        		"	options.ctx.fillStyle = fillStyle;\n" + 
        		"	// Draw several transparent circles with alpha\n" + 
        		"	for (i = 0; i < 30; i++) {\n" + 
        		"		options.ctx.beginPath();\n" + 
        		"		options.ctx.arc(options.center.X,\n" + 
        		"			options.center.Y,\n" + 
        		"			i,\n" + 
        		"			0,\n" + 
        		"			Math.PI,\n" + 
        		"			true);\n" + 
        		"		options.ctx.fill();\n" + 
        		"		options.ctx.stroke();\n" + 
        		"	}\n" + 
        		"}\n" + 
        		"function convertSpeedToAngle(options) {\n" + 
        		"	/* Helper function to convert a speed to the \n" + 
        		"	* equivelant angle.\n" + 
        		"	*/\n" + 
        		"	var iSpeed = (options.speed / 10),\n" + 
        		"	    iSpeedAsAngle = ((iSpeed * 20) + 10) % 180;\n" + 
        		"	// Ensure the angle is within range\n" + 
        		"	if (iSpeedAsAngle > 180) {\n" + 
        		"        iSpeedAsAngle = iSpeedAsAngle - 180;\n" + 
        		"    } else if (iSpeedAsAngle < 0) {\n" + 
        		"        iSpeedAsAngle = iSpeedAsAngle + 180;\n" + 
        		"    }\n" + 
        		"	return iSpeedAsAngle;\n" + 
        		"}\n" + 
        		"function drawNeedle(options) {\n" + 
        		"	/* Draw the needle in a nice read colour at the\n" + 
        		"	* angle that represents the options.speed value.\n" + 
        		"	*/\n" + 
        		"	var iSpeedAsAngle = convertSpeedToAngle(options),\n" + 
        		"	    iSpeedAsAngleRad = degToRad(iSpeedAsAngle),\n" + 
        		"        gaugeOptions = options.gaugeOptions,\n" + 
        		"        innerTickX = gaugeOptions.radius - (Math.cos(iSpeedAsAngleRad) * 20),\n" + 
        		"        innerTickY = gaugeOptions.radius - (Math.sin(iSpeedAsAngleRad) * 20),\n" + 
        		"        fromX = (options.center.X - gaugeOptions.radius) + innerTickX,\n" + 
        		"        fromY = (gaugeOptions.center.Y - gaugeOptions.radius) + innerTickY,\n" + 
        		"        endNeedleX = gaugeOptions.radius - (Math.cos(iSpeedAsAngleRad) * gaugeOptions.radius),\n" + 
        		"        endNeedleY = gaugeOptions.radius - (Math.sin(iSpeedAsAngleRad) * gaugeOptions.radius),\n" + 
        		"        toX = (options.center.X - gaugeOptions.radius) + endNeedleX,\n" + 
        		"        toY = (gaugeOptions.center.Y - gaugeOptions.radius) + endNeedleY,\n" + 
        		"        line = createLine(fromX, fromY, toX, toY, \"rgb(255,0,0)\", 5, 0.6);\n" + 
        		"	drawLine(options, line);\n" + 
        		"	// Two circle to draw the dial at the base (give its a nice effect?)\n" + 
        		"	drawNeedleDial(options, 0.6, \"rgb(127, 127, 127)\", \"rgb(255,255,255)\");\n" + 
        		"	drawNeedleDial(options, 0.2, \"rgb(127, 127, 127)\", \"rgb(127,127,127)\");\n" + 
        		"}\n" + 
        		"function buildOptionsAsJSON(canvas, iSpeed) {\n" + 
        		"	/* Setting for the speedometer \n" + 
        		"	* Alter these to modify its look and feel\n" + 
        		"	*/\n" + 
        		"	var centerX = 210,\n" + 
        		"	    centerY = 210,\n" + 
        		"        radius = 140,\n" + 
        		"        outerRadius = 200;\n" + 
        		"	// Create a speedometer object using Javascript object notation\n" + 
        		"	return {\n" + 
        		"		ctx: canvas.getContext('2d'),\n" + 
        		"		speed: iSpeed,\n" + 
        		"		center:	{\n" + 
        		"			X: centerX,\n" + 
        		"			Y: centerY\n" + 
        		"		},\n" + 
        		"		levelRadius: radius - 10,\n" + 
        		"		gaugeOptions: {\n" + 
        		"			center:	{\n" + 
        		"				X: centerX,\n" + 
        		"				Y: centerY\n" + 
        		"			},\n" + 
        		"			radius: radius\n" + 
        		"		},\n" + 
        		"		radius: outerRadius\n" + 
        		"	};\n" + 
        		"}\n" + 
        		"function clearCanvas(options) {\n" + 
        		"	options.ctx.clearRect(0, 0, 800, 600);\n" + 
        		"	applyDefaultContextSettings(options);\n" + 
        		"}\n" + 
        		"function draw() {\n" + 
        		"	/* Main entry point for drawing the speedometer\n" + 
        		"	* If canvas is not support alert the user.\n" + 
        		"	*/\n" + 
        		"		\n" + 
        		"	console.log('Target: ' + iTargetSpeed);\n" + 
        		"	console.log('Current: ' + iCurrentSpeed);\n" + 
        		"	\n" + 
        		"	var canvas = document.getElementById('tutorial'),\n" + 
        		"	    options = null;\n" + 
        		"	// Canvas good?\n" + 
        		"	if (canvas !== null && canvas.getContext) {\n" + 
        		"		options = buildOptionsAsJSON(canvas, iCurrentSpeed);\n" + 
        		"	    // Clear canvas\n" + 
        		"	    clearCanvas(options);\n" + 
        		"		// Draw the metallic styled edge\n" + 
        		"		drawMetallicArc(options);\n" + 
        		"		// Draw thw background\n" + 
        		"		drawBackground(options);\n" + 
        		"		// Draw tick marks\n" + 
        		"		drawTicks(options);\n" + 
        		"		// Draw labels on markers\n" + 
        		"		drawTextMarkers(options);\n" + 
        		"		// Draw speeometer colour arc\n" + 
        		"		drawSpeedometerColourArc(options);\n" + 
        		"		// Draw the needle and base\n" + 
        		"		drawNeedle(options);\n" + 
        		"		\n" + 
        		"	} else {\n" + 
        		"		alert(\"Canvas not supported by your browser!\");\n" + 
        		"	}\n" + 
        		"	\n" + 
        		"	if(iTargetSpeed == iCurrentSpeed) {\n" + 
        		"		clearTimeout(job);\n" + 
        		"		return;\n" + 
        		"	} else if(iTargetSpeed < iCurrentSpeed) {\n" + 
        		"		bDecrement = true;\n" + 
        		"	} else if(iTargetSpeed > iCurrentSpeed) {\n" + 
        		"		bDecrement = false;\n" + 
        		"	}\n" + 
        		"	\n" + 
        		"	if(bDecrement) {\n" + 
        		"		if(iCurrentSpeed - 10 < iTargetSpeed)\n" + 
        		"			iCurrentSpeed = iCurrentSpeed - 1;\n" + 
        		"		else\n" + 
        		"			iCurrentSpeed = iCurrentSpeed - 5;\n" + 
        		"	} else {\n" + 
        		"	\n" + 
        		"		if(iCurrentSpeed + 10 > iTargetSpeed)\n" + 
        		"			iCurrentSpeed = iCurrentSpeed + 1;\n" + 
        		"		else\n" + 
        		"			iCurrentSpeed = iCurrentSpeed + 5;\n" + 
        		"	}\n" + 
        		"	\n" + 
        		"	job = setTimeout(\"draw()\", 5);\n" + 
        		"}\n" + 
        		"function drawWithInputValue() {\n" + 
        		"	var txtSpeed = document.getElementById('txtSpeed');\n" + 
        		"	if (txtSpeed !== null) {\n" + 
        		"        iTargetSpeed = txtSpeed.value;\n" + 
        		"		// Sanity checks\n" + 
        		"		if (isNaN(iTargetSpeed)) {\n" + 
        		"			iTargetSpeed = 0;\n" + 
        		"		} else if (iTargetSpeed < 0) {\n" + 
        		"			iTargetSpeed = 0;\n" + 
        		"		} else if (iTargetSpeed > 80) {\n" + 
        		"			iTargetSpeed = 80;\n" + 
        		"        }\n" + 
        		"        job = setTimeout(\"draw()\", 5);\n" + 
        		" \n" + 
        		"    }\n" + 
        		"}</script>\n" + 
        		"	</head>\n" + 
        		"	<body onload='draw(0);'>\n" + 
        		"		<canvas id=\"tutorial\" width=\"440\" height=\"220\">\n" + 
        		"			Canvas not available.\n" + 
        		"		</canvas>\n" + 
        		"		<div>\n" + 
        		"			<form id=\"drawTemp\">\n" + 
        		"				<input type=\"text\" id=\"txtSpeed\" name=\"txtSpeed\" value=\"0\" maxlength=\"2\"/>\n" + 
        		"				<input type=\"button\" value=\"Draw\" onclick=\"drawWithInputValue();\">\n" + 
        		"			</form>\n" + 
        		"		</div>\n" + 
        		"  \n" + 
        		"  </div>\n" + 
        		"\n" + 
        		"\n" + 
        		"\n" + 
        		"<div id=\"right_panel\" style=\"background-image: url('mapfinal.jpg'); border: 1px solid black;\"> \n" + 
        		"	\n" + 
        		"	<div id=\"dot\"></div>	\n" + 
        		"	\n" + 
        		"</div>\n" + 
        		"	\n" + 
        		"</div>\n" + 
        		"<script>\n" + 
        		"var dot = document.getElementById('dot');\n" + 
        		"var container = document.getElementById('right_panel');\n" + 
        		"function placeDiv(x_pos, y_pos) {\n" + 
        		"  dot.style.position = \"absolute\";\n" + 
        		"  dot.style.left = x_pos+'px';\n" + 
        		"  dot.style.top = y_pos+'px';\n" + 
        		"}\n" + 
        		"lat_in = 40.580190\n" + 
        		"long_in = -98.349237\n" + 
        		"x = (long_in-(-98.353633))*49694.39277	\n" + 
        		"y = 454-((lat_in-40.575737)*73617.64229)\n" + 
        		"placeDiv(x,y);\n" + 
        		"</script>\n" + 
        		"\n" + 
        		"\n" + 
        		"\n" + 
        		"</body>\n" + 
        		"\n" + 
        		"\n" + 
        		"\n" + 
        		"</html>";
        
        // create a document, set it on the jeditorpane, then add the html
        Document doc = kit.createDefaultDocument();
        jEditorPane.setDocument(doc);
        jEditorPane.setText(htmlString);

        // now add it all to a frame
        JFrame j = new JFrame("HtmlEditorKit Test");
        //j.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // make it easy to close the application
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // display the frame
        j.setSize(new Dimension(800,480));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        j.setBounds(0,0,screenSize.width, screenSize.height);
        j.setVisible(true);

        j.pack();

        
        // pack it, if you prefer
        //j.pack();
        
        // center the jframe, then make it visible
        j.setLocationRelativeTo(null);
        j.setVisible(true);
      }
    });
  }
}