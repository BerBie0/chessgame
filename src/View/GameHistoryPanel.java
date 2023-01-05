package View;

import Controller.GameController;
import Controller.GameManager;
import Model.Board.Board;
import Model.Move.IMove;
import Model.utils.Color2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static View.GameFrame2.*;

public class GameHistoryPanel extends JPanel {
    /*-------------------------------------------ATTRIBUTS------------------------------------------------------------*/
    /*-------------------------------------------CONSTRUCTORS---------------------------------------------------------*/
    /*---------------------------------------------GET SET------------------------------------------------------------*/
    /*-------------------------------------------OVERRIDE METHOD------------------------------------------------------*/
    /*-------------------------------------------INTERFACE METHOD-----------------------------------------------------*/
    /*------------------------------------------------METHOD----------------------------------------------------------*/

    private final DataModel model;
    private final JScrollPane scrollPane;
    private String whiteName;
    private String blackName;
    private static final Dimension HISTORY_PANEL_DIM = new Dimension(200, 400);

    GameHistoryPanel(String whiteName, String blackName) {
        this.setLayout(new BorderLayout());
        this.model = new DataModel(whiteName, blackName);
        final JTable table = new JTable(model);
        table.setRowHeight(15);
        this.scrollPane = new JScrollPane(table);
        scrollPane.setColumnHeaderView(table.getTableHeader());
        scrollPane.setPreferredSize(HISTORY_PANEL_DIM);
        this.add(scrollPane, BorderLayout.CENTER);
        this.setVisible(true);
    }

    void redo(final Board board, final MoveLog moveLog) {
        int currentRow = 0;
        this.model.clear();
        for (final IMove move : moveLog.getMoves()) {
            final String moveText = move.getNewPos()+"";
            if(move.getPiece().isWhite()) {
                this.model.setValueAt(moveText, currentRow, 0);
            } else if (!move.getPiece().isWhite()) {
                this.model.setValueAt(moveText, currentRow, 1);
                currentRow++;
            }
        }

        
        final JScrollBar vertical = scrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }

    private static class DataModel extends DefaultTableModel {
        private final List<Row> values;
        //private final static String[] NAMES = {"White", "Black"};
        private final String[] NAMES;

        DataModel(String whiteName, String blackName) {
            this.values = new ArrayList<>();
            NAMES = new String[]{whiteName, blackName};
        }

        public void clear() {
            this.values.clear();
        }

        @Override
        public int getRowCount() {
            if (this.values == null)
                return 0;
            return this.values.size();
        }

        @Override
        public int getColumnCount() {
            return NAMES.length;
        }

        @Override
        public Object getValueAt(final int row, final int col) {
            final Row currentRow = this.values.get(row);
            if (col == 0)
                return currentRow.getWhiteMove();
            else if (col == 1)
                return currentRow.getBlackMove();
            return null;
        }

        @Override
        public void setValueAt(final Object aValue, final int row, final int col) {
            final Row currentRow;

            if (this.values.size() <= row) {
                currentRow = new Row();
                this.values.add(currentRow);
            } else {
                currentRow = this.values.get(row);
            }
            if (col == 0) {
                currentRow.setWhiteMove((String) aValue);
                fireTableRowsInserted(row, row);
            } else if (col == 1) {
                currentRow.setBlackMove((String) aValue);
                fireTableCellUpdated(row, col);
            }
            //fireTableCellUpdated(row,col);
        }

        @Override
        public Class<?> getColumnClass(final int columnIndex) {
            return Integer.class;
        }

        @Override
        public String getColumnName(final int column) {
            return NAMES[column];
        }
    } //end datamodel

    private static class Row {
        private String whiteMove;
        private String blackMove;

        Row() {

        }

        public String getWhiteMove() {
            return this.whiteMove;
        }

        public String getBlackMove() {
            return this.blackMove;
        }

        public void setWhiteMove(final String move) {
            this.whiteMove = move;
        }

        public void setBlackMove(final String move) {
            this.blackMove = move;
        }
    }//end Row

} // end GameHistoryPanel
