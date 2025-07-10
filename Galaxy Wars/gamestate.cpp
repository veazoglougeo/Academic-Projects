#include "gamestate.h"
#include "level.h"
#include "player.h"
#include "thread"
#include "chrono"
#include "iostream"
#include "sgg/util.h"
#include <stdio.h>
using namespace std;
using namespace graphics;
GameState::GameState()
{
}
void GameState::init()
{
	playSound(getFullAssetPath("music.mp3"), 0.2f, true);
	current = home_page;
	m_current_level = new Level("level");
	m_current_level->init();

	m_player = new Player("Player");
	m_player->init();
	
}


void GameState::draw()
{
	switch (current){
	case home_page:
		drawHome();
		break;
	case game_page:
		drawGame();
		break;
	case game_over:
		drawGameOver();
		break;
	}
}

void GameState::update(float dt)
{
	/*Assure that the game starts after pressing the button.*/
	if (current != game_page || !m_current_level) {
		return; 
	}
	m_current_level->update(dt);
}

void GameState::drawHome() {
	Brush br;
	br.texture = getFullAssetPath("background.png");
	br.outline_opacity = 0.0f;
	float w = getCanvasWidth();
	float h = getCanvasHeight();
	drawRect(w / 2, h / 2, w, h, br);
	br.texture = getFullAssetPath("logo.png");
	drawRect(w / 2, h / 1.8f, w / 3, h / 3.5f, br);
	br.texture = getFullAssetPath("start_button.png");
	drawRect(w / 2, h / 1.3f, w / 4, h / 10, br);
	setFont(getFullAssetPath("arc.ttf"));
	drawText(w / 30, h / 1.01f, 0.25f, "move: w,a,s,d", br);
	drawText(w / 1.7f, h / 1.01f, 0.25f, "shoot: space", br);
	setFont(getFullAssetPath("ka1.ttf"));
	MouseState ms;
	getMouseState(ms);
	if (ms.cur_pos_x > 283 && ms.cur_pos_x < 466 &&
		ms.cur_pos_y > 543 && ms.cur_pos_y < 612){
		br.texture = getFullAssetPath("start_button_hover.png");
		drawRect(w / 2, h / 1.3f, w / 4, h / 10, br);
		if (ms.button_left_down) {
			playSound(getFullAssetPath("click.mp3"), 1.0f, false);
			/*Slight delay to make it smoother.*/
			this_thread::sleep_for(std::chrono::milliseconds(800));
			current = game_page;
		}
	}
}
void GameState::drawGame() {
	if (!m_current_level) {
		return;
	}
	m_current_level->draw();
}

void GameState::drawGameOver(){
	static bool button_pressed = false;
	static bool state_initialized = false;
	if (!state_initialized) {
		state_initialized = true;
		button_pressed = true;
	}
	Brush br;
	br.texture = getFullAssetPath("game_over.png");
	br.outline_opacity = 0.0f;
	float w = getCanvasWidth();
	float h = getCanvasHeight();
	drawRect(w / 2, h / 2, w, h, br);
	drawText(w / 2.6, h / 2.8f, 0.27, "score", br);
	string disp_score = to_string(getLevel()->getScore());
	drawText(w / 2.1, h / 2.4f, 0.27, disp_score, br);
	br.texture = getFullAssetPath("try_again.png");
	drawRect(w / 2, h / 1.5f, w / 4, h / 6, br);
	br.texture = getFullAssetPath("exit_button.png");
	drawRect(w / 2, h / 1.2, w / 4, h / 10, br);
	MouseState ms;
	getMouseState(ms);
	if (ms.cur_pos_x > 283 && ms.cur_pos_x < 466 &&
		ms.cur_pos_y > 440 && ms.cur_pos_y < 560) {
		br.texture = getFullAssetPath("try_again_hover.png");
		drawRect(w / 2, h / 1.5f, w / 4, h / 6, br);
		if (ms.button_left_down && !button_pressed) {
			playSound(getFullAssetPath("click.mp3"), 1.0f, false);
			std::this_thread::sleep_for(std::chrono::milliseconds(800));
			button_pressed = true;
			restartGame();
		}
		if (!ms.button_left_down) {
			button_pressed = false;
		}
	}
	if (ms.cur_pos_x > 283 && ms.cur_pos_x < 466 &&
		ms.cur_pos_y > 590 && ms.cur_pos_y < 660) {
		br.texture = getFullAssetPath("exit_button_hover.png");
		drawRect(w / 2, h / 1.2f, w / 4, h / 10, br);
		if (ms.button_left_down && !button_pressed) {
			playSound(getFullAssetPath("click.mp3"), 1.0f, false);
			std::this_thread::sleep_for(std::chrono::milliseconds(800));
			button_pressed = true;
			destroyWindow();
			exit(0);
		}
		if (!ms.button_left_down) {
			button_pressed = false;
		}
	}
}
void GameState::restartGame() {
	if (m_current_level) {
		delete m_current_level;
		m_current_level = nullptr;
	}
	if (m_player->isActive()) {
		delete m_player;
		m_player = nullptr;
	}
	m_current_level = new Level("restart");
	m_current_level->init();
	m_player = new Player("player");
	m_player->init();
	current = game_page;
}
void GameState::setState(string state)
{
	if (state == "home_page") {
		current = home_page;
	}
	if (state == "game_page") {
		current = game_over;
	}
	if (state == "game_over") {
		current = game_over;
	}
	if (state == "close_game") {
		current = close_game;
	}
}
GameState* GameState::getInstance()
{
	if (m_unique_instance == nullptr) {
		m_unique_instance = new GameState();
	}
	return m_unique_instance;
}

GameState::~GameState()
{

	if (m_player) {
		delete m_player;
	}
	if (m_current_level) {
		delete m_current_level;
	}
}

string GameState::getAssetDir()
{
	return m_asset_path;
}

string GameState::getFullAssetPath(const string& asset)
{
	return m_asset_path + asset;
}

GameState* GameState::m_unique_instance = nullptr;