-- Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd.
CREATE TABLE wms_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(32) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(50) NOT NULL,
    role VARCHAR(20) NOT NULL,
    warehouse VARCHAR(50),
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE wms_task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    task_no VARCHAR(32) NOT NULL UNIQUE,
    type VARCHAR(24) NOT NULL,
    status VARCHAR(20) NOT NULL,
    priority VARCHAR(12) NOT NULL,
    warehouse_name VARCHAR(60) NOT NULL,
    zone_code VARCHAR(20) NOT NULL,
    source_location VARCHAR(30),
    target_location VARCHAR(30),
    sku_code VARCHAR(40) NOT NULL,
    product_name VARCHAR(120) NOT NULL,
    batch_no VARCHAR(40),
    planned_qty INT NOT NULL,
    completed_qty INT NOT NULL,
    assignee VARCHAR(40),
    due_at DATETIME(6) NOT NULL,
    started_at DATETIME(6),
    completed_at DATETIME(6),
    remark VARCHAR(200),
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    INDEX idx_wms_task_status (status),
    INDEX idx_wms_task_assignee (assignee)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE wms_inventory (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    sku_code VARCHAR(40) NOT NULL,
    product_name VARCHAR(120) NOT NULL,
    warehouse_name VARCHAR(60) NOT NULL,
    zone_code VARCHAR(20) NOT NULL,
    location_code VARCHAR(30) NOT NULL,
    batch_no VARCHAR(40) NOT NULL,
    quality_status VARCHAR(16) NOT NULL,
    available_qty INT NOT NULL,
    allocated_qty INT NOT NULL,
    inbound_qty INT NOT NULL,
    unit VARCHAR(12) NOT NULL,
    expiry_date DATE,
    last_count_at DATETIME(6),
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    CONSTRAINT uk_wms_inventory UNIQUE (sku_code, location_code, batch_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE wms_inbound_receipt (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    receipt_no VARCHAR(32) NOT NULL UNIQUE,
    supplier_name VARCHAR(100) NOT NULL,
    source_type VARCHAR(30) NOT NULL,
    warehouse_name VARCHAR(60) NOT NULL,
    dock_code VARCHAR(30) NOT NULL,
    status VARCHAR(20) NOT NULL,
    line_count INT NOT NULL,
    planned_qty INT NOT NULL,
    received_qty INT NOT NULL,
    appointment_at DATETIME(6) NOT NULL,
    operator_name VARCHAR(40),
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE wms_outbound_wave (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    wave_no VARCHAR(32) NOT NULL UNIQUE,
    warehouse_name VARCHAR(60) NOT NULL,
    status VARCHAR(20) NOT NULL,
    order_count INT NOT NULL,
    sku_count INT NOT NULL,
    piece_count INT NOT NULL,
    picked_qty INT NOT NULL,
    carrier_name VARCHAR(40) NOT NULL,
    cutoff_at DATETIME(6) NOT NULL,
    owner_name VARCHAR(40),
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE wms_warehouse_zone (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    zone_code VARCHAR(20) NOT NULL UNIQUE,
    zone_name VARCHAR(60) NOT NULL,
    warehouse_name VARCHAR(60) NOT NULL,
    type VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    location_count INT NOT NULL,
    used_location_count INT NOT NULL,
    occupancy_rate INT NOT NULL,
    environment VARCHAR(30),
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
