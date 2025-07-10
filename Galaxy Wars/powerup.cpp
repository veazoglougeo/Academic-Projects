#include "powerup.h"
#include "sgg/graphics.h"
#include "gamestate.h"
PowerUp::PowerUp(float  x, float size, Type type) : GameObject("PowerUp"), m_type(type), m_size(size) {
	m_pos_x = x;
	m_pos_y = -m_size;	
}
Disk PowerUp::getCollisionHull() const
{
	Disk disk;
	disk.cx = m_pos_x;
	disk.cy = m_pos_y;
	disk.radius = m_size / 2.0f;
	return disk;
}
void PowerUp::update(float dt) {
	m_pos_y += m_velocity * dt / 1000.0f;
	GameObject::update(dt);
}
void PowerUp::draw() {
	if (m_type == health) {
		m_powerup_brush.texture = m_state->getFullAssetPath("health.png");
	}
	else if (m_type == speed) {
		m_powerup_brush.texture = m_state->getFullAssetPath("speed.png");
	}
	m_powerup_brush.outline_opacity = 0.0f;
	drawRect(m_pos_x, m_pos_y, 0.4f, 0.4f, m_powerup_brush);
}
bool PowerUp::outOfBounds(float cH) {
	return m_pos_y > cH + m_size;
}
