package com.spiritflightapps.berg;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.inqbarna.tablefixheaders.TableFixHeaders;

import java.util.ArrayList;

public class AssessmentActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.table);

		TableFixHeaders tableFixHeaders = (TableFixHeaders) findViewById(R.id.table);
        ArrayList<Assessment> assessments = Assessment.getDummyAssessments(); //TODO: Load from cursor
		tableFixHeaders.setAdapter(new MyAdapter(this, assessments));
	}
    //TODO: Definitely put this in its own java file with a reasonable name.
	public class MyAdapter extends SampleTableAdapter {

		private final int width;
		private final int height;
        private Resources resources;
        private ArrayList<Assessment> mAssessments;
		public MyAdapter(Context context,ArrayList<Assessment> assessments) {
			super(context);
            this.mAssessments = assessments;
			resources = context.getResources();

			width = resources.getDimensionPixelSize(R.dimen.table_width);
			height = resources.getDimensionPixelSize(R.dimen.table_height);
		}

		@Override
		public int getRowCount() {
			return resources.getStringArray(R.array.question_labels).length + 1;
		}

		@Override
		public int getColumnCount() {
			return mAssessments.size();
		}

        @Override
        public View getView(int row, int column, View converView, ViewGroup parent) {
            View view = super.getView(row,column,converView,parent);
            EditText editText = (EditText) view.findViewWithTag("answer");
            if (editText !=null ) {
                mAssessments.get(column).addEditText(editText);
            }
            return view;
        }

            /**
             * width * 5 is a quick way to make it 'about right' for now. TODO: Design work.
             * @param column
             *            the column. If the column is <code>-1</code> it is the header.
             * @return
             */
		@Override
		public int getWidth(int column) {

            if (column==-1) {  //the wide fixed column on the left for instructions
                return width * 5; //TODO: Use
            }
            return width;
		}

		@Override
		public int getHeight(int row) {
			return height;
		}

		@Override
		public String getCellString(int row, int column) {
            if ((row == -1) && (column == -1)) {
                return resources.getString(R.string.label_column_header);
            }
            if (row == -1) {
                return (column + 1) + "E"; //TODO: Cleanup
            }
            if (column == -1) {  //get instruction text...
                if (row == getRowCount() - 1) {
                    return resources.getString(R.string.total);
                } else {
                    return resources.getStringArray(R.array.question_labels)[row];
                }
            }

            if ((column > -1) && (row == getRowCount() -1)) { //Total row. (TODO: Make more clear
                return mAssessments.get(column).getTotalString();
            }

            //return  row + ". " + column;
            return mAssessments.get(column).answers.get(row); //todo: 1-4
            //TODO: Initialize and don't overwrite, hello.... or make sure and update the object ontextchanged, etc.
		}



		@Override
		public int getLayoutResource(int row, int column) {
			final int layoutResource;
			switch (getItemViewType(row, column)) {
				case 0:
					layoutResource = R.layout.item_table1_header;
				break;
				case 1:
					layoutResource = R.layout.item_answer;  //regular cell.
				break;
                case 2:
                    layoutResource = R.layout.item_question_label_column;
                break;
                case 3:
                    layoutResource = R.layout.item_footer;
                    break;
				default:
					throw new RuntimeException("should not happen..."); //TODO: Something else.
			}
			return layoutResource;
		}

        /**
         *
         * @param row
         *            The row of the item within the adapter's data table of the
         *            item whose view we want. If the row is <code>-1</code> it is
         *            the header.
         * @param column
         *            The column of the item within the adapter's data table of the
         *            item whose view we want. If the column is <code>-1</code> it
         *            is the header.
         * @return 0 for header type cell, 1 for regular cell and 2 for instructions cell.
         */
		@Override
		public int getItemViewType(int row, int column) {
			int retval = 1; //TODO: Convert to enum, MUCH more readable...
            if ((row > -1) && (column < 0)) {
                retval = 2;
            } else if (row < 0) {
				retval = 0;
			} else {
				retval = 1;
			}

            if ((column > -1) && (row == getRowCount() - 1)) {
                retval = 3;
            }

            return retval;
		}

		@Override
		public int getViewTypeCount() {
			return 4;
		}
	}
}
