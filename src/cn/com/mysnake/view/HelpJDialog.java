package cn.com.mysnake.view;

import com.cloudgarden.layout.AnchorConstraint;
import com.cloudgarden.layout.AnchorLayout;

import javax.swing.*;

/**
 * °ïÖú¶Ô»°¿ò
 */
public class HelpJDialog extends javax.swing.JDialog{

    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;

    /**
     * Auto-generated main method to display this JDialog
     */

    public HelpJDialog(JFrame frame) {
        super(frame);
        initGUI();
    }

    private void initGUI() {
        try {
            AnchorLayout thisLayout = new AnchorLayout();
            getContentPane().setLayout(thisLayout);
            {
                jLabel5 = new JLabel();
                getContentPane().add(jLabel5, new AnchorConstraint(791, 896, 856, 701, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
                jLabel5.setText("\u4f5c\u8005\u003a\u5f20\u5b63\u002c\u6768\u65b0\u5b87\u002c\u5f20\u6e05\u590f");
                jLabel5.setPreferredSize(new java.awt.Dimension(106, 17));
            }
            {
                jLabel4 = new JLabel();
                getContentPane().add(jLabel4, new AnchorConstraint(410, 463, 475, 94, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
                jLabel4.setText("\u5728\u8bbe\u7f6e\u4e2d\u53ef\u4ee5\u66f4\u6539\u7528\u6237\u540d");
                jLabel4.setPreferredSize(new java.awt.Dimension(201, 17));
            }
            {
                jLabel3 = new JLabel();
                getContentPane().add(jLabel3, new AnchorConstraint(299, 970, 364, 95, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
                jLabel3.setText("\u5982\u679c\u5728\u540c\u4e00\u53f0\u7535\u8111\u4e0a\u8fd0\u884c\u591a\u4e2a\u672c\u7a0b\u5e8f\uff0c\u9700\u8981\u5236\u5b9a\u4e0d\u540c\u7684\u7528\u6237\u540d");
                jLabel3.setPreferredSize(new java.awt.Dimension(336, 17));
            }
            {
                jLabel2 = new JLabel();
                getContentPane().add(jLabel2, new AnchorConstraint(188, 626, 253, 95, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
                jLabel2.setText("\u53ef\u4ee5\u901a\u8fc7\u591a\u4eba\u6e38\u620f\u6765\u521b\u5efa\u6216\u8005\u52a0\u5165\u6e38\u620f");
            }
            {
                jLabel1 = new JLabel();
                getContentPane().add(jLabel1, new AnchorConstraint(78, 777, 143, 95, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
                jLabel1.setText("\u672c\u6e38\u620f\u4f7f\u7528\u65b9\u5411\u952e\u6765\u63a7\u5236\u86c7\u7684\u79fb\u52a8");
                jLabel1.setPreferredSize(new java.awt.Dimension(262, 17));
            }
            this.setSize(561, 300);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
