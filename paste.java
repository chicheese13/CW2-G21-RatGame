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