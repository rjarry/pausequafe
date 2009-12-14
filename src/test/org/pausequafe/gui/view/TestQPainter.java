package org.pausequafe.gui.view;

import org.pausequafe.misc.util.Constants;

import com.trolltech.qt.core.QSize;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QImage;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

public class TestQPainter {

    /**
     * @param args
     */
    public static void main(String[] args) {
        QApplication.initialize(args);

        QWidget w = new QWidget(null);
        QLabel source = new QLabel("", w);
        source.setGeometry(0, 0, 64, 64);
        QLabel result = new QLabel("", w);
        result.setGeometry(0, 0, 64, 64);
        QVBoxLayout layout = new QVBoxLayout();
        w.setLayout(layout);
        layout.addWidget(source);
        layout.addWidget(result);

        String sourceIcon = "resources/icons/EVE/icon23_11.png";

        source.setPixmap(new QPixmap(sourceIcon));
//         result.setPixmap(mergeBlueprintBG(sourceIcon, destIcon));
        result.setPixmap(mergeBlueprintBG(sourceIcon));

        w.show();

        QApplication.exec();
    }

    private static QPixmap mergeBlueprintBG(String icon) {
        QSize resultSize = new QSize(64, 64);
        QImage blueprintBG = new QImage(Constants.BLUEPRINTBG_ICON);
        QImage itemIcon = new QImage(icon);
        QImage resultImage = new QImage(resultSize, QImage.Format.Format_ARGB32_Premultiplied);
        
        QPainter painter = new QPainter(resultImage);
        painter.setCompositionMode(QPainter.CompositionMode.CompositionMode_Source);
        painter.fillRect(resultImage.rect(), Qt.GlobalColor.transparent);
        painter.setCompositionMode(QPainter.CompositionMode.CompositionMode_SourceOver);
        painter.drawImage(0, 0, itemIcon);
        painter.setCompositionMode(QPainter.CompositionMode.CompositionMode_Multiply);
        painter.drawImage(0, 0, blueprintBG);
        painter.setCompositionMode(QPainter.CompositionMode.CompositionMode_DestinationOver);
        painter.fillRect(resultImage.rect(), Qt.GlobalColor.white);
        painter.end();

        return QPixmap.fromImage(resultImage);
    }
}
