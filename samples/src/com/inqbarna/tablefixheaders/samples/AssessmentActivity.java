package com.inqbarna.tablefixheaders.samples;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import com.inqbarna.tablefixheaders.TableFixHeaders;
import com.inqbarna.tablefixheaders.samples.adapters.SampleTableAdapter;

public class AssessmentActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.table);

		TableFixHeaders tableFixHeaders = (TableFixHeaders) findViewById(R.id.table);
		tableFixHeaders.setAdapter(new MyAdapter(this));
	}

	public class MyAdapter extends SampleTableAdapter {

		private final int width;
		private final int height;
        private Resources resources;
		public MyAdapter(Context context) {
			super(context);

			resources = context.getResources();

			width = resources.getDimensionPixelSize(R.dimen.table_width);
			height = resources.getDimensionPixelSize(R.dimen.table_height);
		}

		@Override
		public int getRowCount() {
			return resources.getStringArray(R.array.question_labels).length;
		}

		@Override
		public int getColumnCount() {
			return 6;
		} //TODO: Dynamic

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
            if ((row==-1) && (column ==-1)) {
                return resources.getString(R.string.label_column_header); //TODO: strings.xml
            }
            if (row==-1) {
                return (column+1)+"E";
            }
            if (column==-1) {  //get instruction text...
               // return "Q" + row + ".  Instruction text.";
                return resources.getStringArray(R.array.question_labels)[row];
            }
            //return  row + ". " + column;
            return String.valueOf(column); //todo: 1-4
		}

		@Override
		public int getLayoutResource(int row, int column) {
			final int layoutResource;
			switch (getItemViewType(row, column)) {
				case 0:
					layoutResource = R.layout.item_table1_header;
				break;
				case 1:
					layoutResource = R.layout.item_table1;
				break;
                case 2:
                    layoutResource = R.layout.item_question_label_column;
                break;
				default:
					throw new RuntimeException("wtf?");
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
         * @return 0 for header type cell, 1 for regular cell and 2 for
         */
		@Override
		public int getItemViewType(int row, int column) {
			if ((row > -1) && (column < 0)) {
                return 2;
            } else if (row < 0) {
				return 0;
			} else {
				return 1;
			}
		}

		@Override
		public int getViewTypeCount() {
			return 3;
		}
	}
}
