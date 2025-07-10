#include "sgg/graphics.h"
#include <string>
#include "sgg/util.h"
#include "gamestate.h"
using namespace graphics;
using namespace std;

void draw(){
	GameState::getInstance()->draw();
}

void update(float dt) {
	GameState::getInstance()->update(dt);
}

void init() {
	GameState::getInstance()->init();
}

int main(int argc, char** argv) {
	createWindow(750, 750, "Galaxy Wars");
	init();
	setDrawFunction(draw);
	setUpdateFunction(update);
	setCanvasSize(GameState::getInstance()->getCanvasWidth(), GameState::getInstance()->getCanvasHeight());
	setCanvasScaleMode(CANVAS_SCALE_FIT);
	startMessageLoop();
}