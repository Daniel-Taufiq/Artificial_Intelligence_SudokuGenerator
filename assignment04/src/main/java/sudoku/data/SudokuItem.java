package sudoku.data;

import gridgames.data.item.Item;

public enum SudokuItem implements Item {
	ONE,
	TWO,
	THREE,
	FOUR,
	FIVE,
	SIX,
	SEVEN,
	EIGHT,
	NINE,
	EMPTY;
	
	public static SudokuItem[] SUDOKU_ITEMS = {ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, EMPTY};
	
	public static SudokuItem convertCharToItem(char digit) {
		if(digit == '1') {
			return ONE;
		} else if(digit == '2') {
			return TWO;
		} else if(digit == '3') {
			return THREE;
		} else if(digit == '4') {
			return FOUR;
		} else if(digit == '5') {
			return FIVE;
		} else if(digit == '6') {
			return SIX;
		} else if(digit == '7') {
			return SEVEN;
		} else if(digit == '8') {
			return EIGHT;
		} else if(digit == '9') {
			return NINE;
		} else {
			return EMPTY;
		}
	}
	
	@Override
	public boolean isHiddenItem() {
		return false;
	}
	
	@Override
    public String toString() {
        if(this.equals(ONE)) {
            return "1";
        } else if(this.equals(TWO)) {
        	return "2";
        } else if(this.equals(THREE)) {
            return "3";
        } else if(this.equals(FOUR)) {
        	return "4";
        } else if(this.equals(FIVE)) {
            return "5";
        } else if(this.equals(SIX)) {
        	return "6";
        } else if(this.equals(SEVEN)) {
            return "7";
        } else if(this.equals(EIGHT)) {
        	return "8";
        } else if(this.equals(NINE)) {
        	return "9";
        } else {
            return "";
        }
    }
}
