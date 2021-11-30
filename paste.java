import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

if (testLevel.getRenderObjects().get(i2).getObjectPosition()[0] <= plusX
						&& testLevel.getRenderObjects().get(i2).getObjectPosition()[0] >= testLevel.getRenderObjects().get(i).getObjectPosition()[0]) {
						//System.out.println("Right collision detected");
						rightCollide = true;
					}
					
					if (testLevel.getRenderObjects().get(i2).getObjectPosition()[1] >= minusY 
						&& testLevel.getRenderObjects().get(i2).getObjectPosition()[1] <= testLevel.getRenderObjects().get(i).getObjectPosition()[1]) {
						//System.out.println("Top collision detected");
						topCollide = true;
					}
					
					if (testLevel.getRenderObjects().get(i2).getObjectPosition()[1] <= plusY
						&& testLevel.getRenderObjects().get(i2).getObjectPosition()[1] >= testLevel.getRenderObjects().get(i).getObjectPosition()[1]) {
						//System.out.println("Bottom collision detected");
						bottomCollide = true;
					}
					
					
					
					
					
					
					
					//get the current decimal
					//minus it from one to get the left over amount of tile
					//divide tile by new speed
					//if it's decimal math.ceil
					//multiply the new speed by math.ceil value
					//minus 1 this will get us the left over amount of tile
					//minus the left over from the current position.
					
					
					//Math.floor of the current of value
					BigDecimal flooredX = position.getPosition()[0].setScale(0, RoundingMode.DOWN);
					BigDecimal flooredY = position.getPosition()[1].setScale(0, RoundingMode.DOWN);
					
					//get the decimal position
					BigDecimal remainderX = position.getPosition()[0].subtract(flooredX);
					BigDecimal remainderY = position.getPosition()[1].subtract(flooredY);
					
					//minus the decimals from one.
					BigDecimal leftoverX = new BigDecimal("1").subtract(remainderX);
					BigDecimal leftoverY = new BigDecimal("1").subtract(remainderY);
					
					//divide the left over by the new speed.
					BigDecimal roundCheckX = new BigDecimal("1").divide(leftoverX, MathContext.DECIMAL128);
					BigDecimal roundCheckY = new BigDecimal("1").divide(leftoverY, MathContext.DECIMAL128);
					
					BigDecimal newX = position.getPosition()[0];
					BigDecimal newY = position.getPosition()[1];
					
					//if there is a remainder then we need to recallibrate
					if (roundCheckX.stripTrailingZeros().scale() > 0) {
						//do the math.ceil on the roundCheck
						BigDecimal ceiledRoundCheckX = roundCheckX.setScale(0, RoundingMode.UP);
						BigDecimal distanceOver = ((ceiledRoundCheckX.multiply(newSpeed)).add(remainderX)).subtract(new BigDecimal("1"));
						newX = position.getPosition()[0].subtract(distanceOver);
					}
					
					if (roundCheckY.stripTrailingZeros().scale() > 0) {
						//do the math.ceil on the roundCheck
						BigDecimal ceiledRoundCheckY = roundCheckY.setScale(0, RoundingMode.UP);
						BigDecimal distanceOver = ((ceiledRoundCheckY.multiply(newSpeed)).add(remainderX)).subtract(new BigDecimal("1"));
						newY = position.getPosition()[1].subtract(distanceOver);
					}
					
					return (new Position(newX, newY));