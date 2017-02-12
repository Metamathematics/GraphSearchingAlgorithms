package jpp.digraph.gui;

public class DiGraphGUI {
	public static void main(String[] args) {
<<<<<<< .mine
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("JPP Graph search Sergey Gerodes");

		controlBar = constructControlBox();

		root = new BorderPane();
		root.setLeft(controlBar);
		graphPane = new Pane();
		root.setCenter(graphPane);
		scene = new Scene(root, width, length);
		window.setScene(scene);
		window.show();
	}

	private Group constructGraphWindow() {
		Group graphGroup = new Group();
		for (CostEdge<XYNode> edge : graph.getEdges()) {
			Node edgeLine = constructEdgeLine(edge);
			edgesDisplayed.put(edge, edgeLine);
			graphGroup.getChildren().add(edgeLine);
		}

		for (CostEdge<XYNode> edge : graph.getEdges()) {
			graphGroup.getChildren().add(constructEdgeDirection(edge));
			graphGroup.getChildren().add(constructEdgeCost(edge));
		}

		for (XYNode node : graph.getNodes()) {
			graphGroup.getChildren().add(constructNodeCircle(node));
			graphGroup.getChildren().add(constructNodeIds(node));
		}

		return graphGroup;
	}

	private Node constructEdgeCost(CostEdge<XYNode> edge) {
		double cx = calculateX(edge) * alpha - 10;
		double cy = calculateY(edge) * beta;
		Label l = new Label(String.valueOf(edge.getCost()));
		l.relocate(cx, cy);
		return l;
	}

	private Node constructEdgeDirection(CostEdge<XYNode> edge) {
		XYNode source = (XYNode) edge.getSource();
		XYNode target = (XYNode) edge.getTarget();
		double ax = source.getX() * alpha;
		double ay = source.getY() * beta;
		double bx = target.getX() * alpha;
		double by = target.getY() * beta;

		double cx = 0.8 * (bx - ax) + ax + (ay - by) / 50;
		double cy = 0.8 * (by - ay) + ay + (bx - ax) / 50;
		Circle c = new Circle(cx, cy, 3);
		c.setFill(Color.YELLOW);

		return c;
	}

	private double calculateX(CostEdge<XYNode> edge) {
		XYNode source = (XYNode) edge.getSource();
		XYNode target = (XYNode) edge.getTarget();
		return calculate(source.getX(), target.getX());
	}

	private double calculateY(CostEdge<XYNode> edge) {
		XYNode source = (XYNode) edge.getSource();
		XYNode target = (XYNode) edge.getTarget();
		return calculate(source.getY(), target.getY());
	}

	private double calculate(double source, double target) {
		double z = 0.7;
		return z * (target - source) + source;
	}

	private Node constructNodeIds(XYNode node) {
		Label l = new Label(node.getId());
		l.relocate(node.getX() * alpha - 12, node.getY() * beta - 7);
		return l;
	}

	private Node constructEdgeLine(CostEdge<XYNode> edge) {
		XYNode source = (XYNode) edge.getSource();
		XYNode target = (XYNode) edge.getTarget();

		double ax = source.getX() * alpha;
		double ay = source.getY() * beta;
		double bx = target.getX() * alpha;
		double by = target.getY() * beta;

		/*
		 * QuadCurve quad = new QuadCurve(); quad.setStartX(ax);
		 * quad.setStartY(ay); quad.setEndX(bx); quad.setEndY(by);
		 * quad.setControlX(bx - ax / 2 + ax); quad.setControlY(by - ay / 2 +
		 * ay); //quad.setFill(Color.TRANSPARENT);
		 */

		Path path = new Path();

		MoveTo moveTo = new MoveTo();
		moveTo.setX(bx);
		moveTo.setY(by);

		QuadCurveTo quadTo = new QuadCurveTo();
		quadTo.setControlX((ax + bx) / 2 + (ay - by) / 20);
		quadTo.setControlY((ay + by) / 2 + (bx - ax) / 20);
		quadTo.setX(ax);
		quadTo.setY(ay);

		path.getElements().add(moveTo);
		path.getElements().add(quadTo);

		//Line line = new Line(ax, ay, bx, by);

		return path;
	}

	private Circle constructNodeCircle(XYNode node) {
		Circle c = new Circle(node.getX() * alpha, node.getY() * beta, 15);
		c.setFill(Color.LIGHTBLUE);
		return c;
	}

	public String fileChose() {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open File");

		File file = fileChooser.showOpenDialog(window);
		if (file != null) {
			return file.getAbsolutePath();
		}
		return null;

	}

	private VBox constructControlBox() {

		// 1--------------------------
		Button graphLoad = new Button("Graph laden");
		graphLoad.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				String filename = fileChose();
				if (filename != null) {
					InputStream is = null;
					try {
						is = new FileInputStream(filename);
						// is = new
						// FileInputStream("/HOME/s323895/Downloads/sample.gxl");
						// is = new
						// FileInputStream("C:/Users/Sergio/Desktop/JPP/sample.gxl");
					} catch (FileNotFoundException exc) {
						exc.printStackTrace();
					}
					GXLSupport<DiGraph<XYNode, CostEdge<XYNode>>, XYNode, CostEdge<XYNode>> support = new XYGXLSupport();
					try {
						graph = support.read(is);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					calculateAlphaBetas();
					dropStart.getItems().clear();
					dropFinish.getItems().clear();

					dropStart.getItems().addAll(graph.getNodes());
					dropFinish.getItems().addAll(graph.getNodes());
					graphPane.getChildren().clear();
					graphPane.getChildren().add(constructGraphWindow());
				}
			}

			private void calculateAlphaBetas() {
				double maxX = 0;
				double maxY = 0;

				for (XYNode node : graph.getNodes()) {
					double x = node.getX();
					double y = node.getY();
					if (maxX < x)
						maxX = x;
					if (maxY < y)
						maxY = y;
					if (minX > x)
						maxX = x;
					if (minY > y)
						maxX = y;
				}
				alpha = (width - 300) / maxX;
				beta = (length - 100) / maxY;
			}
		});
		GridPane.setConstraints(graphLoad, 1, 0);

		// 2----------------------------
		Label nodeCh = new Label("Knoten wählen");
		GridPane.setConstraints(nodeCh, 1, 1);
		Label nodeSt = new Label("Start");
		GridPane.setConstraints(nodeSt, 0, 2);
		Label nodeFi = new Label("Ziel");
		GridPane.setConstraints(nodeFi, 0, 3);

		dropStart = new ChoiceBox<XYNode>();
		GridPane.setConstraints(dropStart, 1, 2);
		dropFinish = new ChoiceBox<XYNode>();
		GridPane.setConstraints(dropFinish, 1, 3);

		// 3---------------------------
		Label searchAlgo = new Label("Such-Algorithmus");
		GridPane.setConstraints(searchAlgo, 1, 4);
		chooseAlgo = new ChoiceBox<String>();
		final String aStarSearch = "A*-Suche";
		final String theBFSearch = "BFS";
		final String theDFSearch = "DFS";
		final String dijkstra = "Dijkstra";
		chooseAlgo.getItems().addAll(aStarSearch, theBFSearch, theDFSearch, dijkstra);
		GridPane.setConstraints(chooseAlgo, 1, 5);

		// 4-------------------

		Button startSearch = new Button("Suche starten");
		startSearch.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				if (dropFinish.getValue() != null && dropStart.getValue() != null && chooseAlgo.getValue() != null) {
					// IDiGraphSearch<DiGraph<XYNode, CostEdge<XYNode>>, XYNode,
					// CostEdge<XYNode>> search = new XYAStar<DiGraph<XYNode,
					// CostEdge<XYNode>>, XYNode, CostEdge<XYNode>>();
					switch (chooseAlgo.getValue()) {
					case aStarSearch:
						search = new XYAStar<DiGraph<XYNode, CostEdge<XYNode>>, XYNode, CostEdge<XYNode>>();
						break;
					case theBFSearch:
						search = new BFS<DiGraph<XYNode, CostEdge<XYNode>>, XYNode, CostEdge<XYNode>>();
						break;
					case theDFSearch:
						search = new DFS<DiGraph<XYNode, CostEdge<XYNode>>, XYNode, CostEdge<XYNode>>();
						break;
					case dijkstra:
						search = new Dijkstra<DiGraph<XYNode, CostEdge<XYNode>>, XYNode, CostEdge<XYNode>>();
						break;
					}

					graphPane.getChildren().clear();
					graphPane.getChildren().add(constructGraphWindow());

					Circle cs = constructNodeCircle(dropStart.getValue());
					Circle cf = constructNodeCircle(dropFinish.getValue());
					cs.setFill(Color.YELLOW);
					cf.setFill(Color.LIGHTSEAGREEN);
					cs.setRadius(1.1 * cs.getRadius());
					cf.setRadius(1.1 * cf.getRadius());
					graphPane.getChildren().addAll(cs, cf, constructNodeIds(dropStart.getValue()),
							constructNodeIds(dropFinish.getValue()));

					search.addListener(new SearchListener());

					Thread thread = new Thread() {
						public void run() {
							try {
								search.search(graph, dropStart.getValue(), dropFinish.getValue());
							} catch (NodeNotExistsException e1) {
								e1.printStackTrace();
							}
						}
					};
					thread.start();

				}
			}
		});
		CheckBox nodeShow = new CheckBox("Knote-Ids anzeigen");
		CheckBox costShow = new CheckBox("Kosten anzeigen");
		CheckBox arrowShow = new CheckBox("Pfeilspitzenanzeigen");
		VBox checkbox = new VBox(startSearch, nodeShow, costShow, arrowShow);
		checkbox.setSpacing(10);
		checkbox.setPadding(new Insets(20));
		// 5----------------------------------------------------
		Label sliderLabel = new Label("Delay:");
		delaySlider = new Slider(0.0, 6.0, 2.0);
=======
>>>>>>> .r17924

	}
}
