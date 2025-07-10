#pragma once
#include <string>
#include <sgg/graphics.h>
using namespace graphics;
using namespace std;


class GameState {
	typedef enum {
		home_page,
		game_page,
		game_over,
		close_game
	}status;
private: 
	string m_asset_path = "assets\\";
	float m_canvas_width = 6.0f;
	float m_canvas_height = 6.0f;

	static GameState* m_unique_instance;
	GameState();


	class Player* m_player = 0;
	class Level* m_current_level = 0;

public:
	status current = home_page;
	float m_global_offset_x = 0.0f;
	float m_global_offset_y = 0.0f;

	void init();
	void draw();
	void update(float dt);
	void drawHome();
	void drawGame();
	void drawGameOver();
	void restartGame();
	void setState(string state);

	static GameState* getInstance();
	~GameState();
	float getCanvasWidth() {
		return m_canvas_width;
	}
	float getCanvasHeight() {
		return m_canvas_height;
	}
	string getAssetDir();
	string getFullAssetPath(const string& asset);
	class Player* getPlayer() { return m_player; }
	class Level* getLevel() { return m_current_level; };
};