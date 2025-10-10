-- DROP SCHEMA public;

CREATE SCHEMA public AUTHORIZATION pg_database_owner;

COMMENT ON SCHEMA public IS 'standard public schema';

-- DROP TYPE public.gtrgm;

CREATE TYPE public.gtrgm (
	INPUT = gtrgm_in,
	OUTPUT = gtrgm_out,
	ALIGNMENT = 4,
	STORAGE = plain,
	CATEGORY = U,
	DELIMITER = ',');
-- public.about_config definition

-- Drop table

-- DROP TABLE public.about_config;

CREATE TABLE public.about_config ( id uuid DEFAULT gen_random_uuid() NOT NULL, is_active bool DEFAULT true NULL, hero_title varchar(500) NULL, hero_subtitle text NULL, mission text NULL, vision text NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, CONSTRAINT about_config_pkey PRIMARY KEY (id));

-- Table Triggers

create trigger set_timestamp_about_config before
update
    on
    public.about_config for each row execute function trigger_set_timestamp();


-- public.contact_config definition

-- Drop table

-- DROP TABLE public.contact_config;

CREATE TABLE public.contact_config ( id uuid DEFAULT gen_random_uuid() NOT NULL, is_active bool DEFAULT true NULL, hero_title varchar(500) NULL, hero_subtitle varchar(1000) NULL, hero_description text NULL, contact_info_title varchar(255) NULL, schedule_title varchar(255) NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, CONSTRAINT contact_config_pkey PRIMARY KEY (id));

-- Table Triggers

create trigger set_timestamp_contact_config before
update
    on
    public.contact_config for each row execute function trigger_set_timestamp();


-- public.contact_items definition

-- Drop table

-- DROP TABLE public.contact_items;

CREATE TABLE public.contact_items ( id uuid DEFAULT gen_random_uuid() NOT NULL, contact_type varchar(20) NULL, icon varchar(50) NULL, "label" varchar(255) NOT NULL, description text NULL, use_global_value bool DEFAULT true NULL, custom_value varchar(500) NULL, display_order int4 DEFAULT 0 NULL, is_active bool DEFAULT true NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, CONSTRAINT contact_items_contact_type_check CHECK (((contact_type)::text = ANY ((ARRAY['phone'::character varying, 'whatsapp'::character varying, 'email'::character varying, 'location'::character varying])::text[]))), CONSTRAINT contact_items_pkey PRIMARY KEY (id));

-- Table Triggers

create trigger set_timestamp_contact_items before
update
    on
    public.contact_items for each row execute function trigger_set_timestamp();


-- public.core_values definition

-- Drop table

-- DROP TABLE public.core_values;

CREATE TABLE public.core_values ( id uuid DEFAULT gen_random_uuid() NOT NULL, title varchar(255) NOT NULL, description text NULL, icon varchar(50) NULL, display_order int4 DEFAULT 0 NULL, is_active bool DEFAULT true NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, CONSTRAINT core_values_pkey PRIMARY KEY (id));

-- Table Triggers

create trigger set_timestamp_core_values before
update
    on
    public.core_values for each row execute function trigger_set_timestamp();


-- public.document_categories definition

-- Drop table

-- DROP TABLE public.document_categories;

CREATE TABLE public.document_categories ( id uuid DEFAULT gen_random_uuid() NOT NULL, "name" varchar(255) NOT NULL, description text NULL, display_order int4 DEFAULT 0 NULL, is_active bool DEFAULT true NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, CONSTRAINT document_categories_pkey PRIMARY KEY (id));

-- Table Triggers

create trigger set_timestamp_document_categories before
update
    on
    public.document_categories for each row execute function trigger_set_timestamp();


-- public.financial_charts definition

-- Drop table

-- DROP TABLE public.financial_charts;

CREATE TABLE public.financial_charts ( id varchar(100) NOT NULL, title varchar(255) NOT NULL, chart_type varchar(20) NULL, display_order int4 DEFAULT 0 NULL, is_active bool DEFAULT true NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, CONSTRAINT financial_charts_chart_type_check CHECK (((chart_type)::text = ANY ((ARRAY['line'::character varying, 'bar'::character varying, 'doughnut'::character varying])::text[]))), CONSTRAINT financial_charts_pkey PRIMARY KEY (id));

-- Table Triggers

create trigger set_timestamp_financial_charts before
update
    on
    public.financial_charts for each row execute function trigger_set_timestamp();


-- public.financial_key_metrics definition

-- Drop table

-- DROP TABLE public.financial_key_metrics;

CREATE TABLE public.financial_key_metrics ( id varchar(100) NOT NULL, "name" varchar(255) NOT NULL, current_value varchar(100) NOT NULL, unit varchar(20) NULL, change_percentage numeric(5, 2) NULL, change_type varchar(10) NULL, icon varchar(50) NULL, description text NULL, "period" varchar(100) NULL, display_order int4 DEFAULT 0 NULL, is_active bool DEFAULT true NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, CONSTRAINT financial_key_metrics_change_type_check CHECK (((change_type)::text = ANY ((ARRAY['increase'::character varying, 'decrease'::character varying])::text[]))), CONSTRAINT financial_key_metrics_pkey PRIMARY KEY (id));

-- Table Triggers

create trigger set_timestamp_financial_key_metrics before
update
    on
    public.financial_key_metrics for each row execute function trigger_set_timestamp();


-- public.financial_reports_config definition

-- Drop table

-- DROP TABLE public.financial_reports_config;

CREATE TABLE public.financial_reports_config ( id uuid DEFAULT gen_random_uuid() NOT NULL, is_active bool DEFAULT true NULL, hero_title varchar(500) NULL, hero_subtitle varchar(500) NULL, hero_description text NULL, hero_access_required bool DEFAULT true NULL, access_title varchar(255) NULL, access_description text NULL, reports_title varchar(255) NULL, reports_subtitle varchar(500) NULL, summary_title varchar(255) NULL, summary_period varchar(100) NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, CONSTRAINT financial_reports_config_pkey PRIMARY KEY (id));

-- Table Triggers

create trigger set_timestamp_financial_reports_config before
update
    on
    public.financial_reports_config for each row execute function trigger_set_timestamp();


-- public.global_schedule definition

-- Drop table

-- DROP TABLE public.global_schedule;

CREATE TABLE public.global_schedule ( id uuid DEFAULT gen_random_uuid() NOT NULL, day_of_week int4 NULL, day_name varchar(20) NOT NULL, opening_time time NULL, closing_time time NULL, is_closed bool DEFAULT false NULL, special_note varchar(255) NULL, schedule_type varchar(20) DEFAULT 'regular'::character varying NULL, special_date date NULL, display_order int4 DEFAULT 0 NULL, is_active bool DEFAULT true NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, CONSTRAINT global_schedule_day_of_week_check CHECK (((day_of_week >= 0) AND (day_of_week <= 6))), CONSTRAINT global_schedule_pkey PRIMARY KEY (id), CONSTRAINT global_schedule_schedule_type_check CHECK (((schedule_type)::text = ANY ((ARRAY['regular'::character varying, 'holiday'::character varying, 'special'::character varying])::text[]))));

-- Table Triggers

create trigger set_timestamp_global_schedule before
update
    on
    public.global_schedule for each row execute function trigger_set_timestamp();


-- public.product_categories definition

-- Drop table

-- DROP TABLE public.product_categories;

CREATE TABLE public.product_categories ( id varchar(50) NOT NULL, "name" varchar(255) NOT NULL, description text NULL, icon varchar(50) NULL, color varchar(50) NULL, display_order int4 DEFAULT 0 NULL, is_active bool DEFAULT true NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, CONSTRAINT product_categories_pkey PRIMARY KEY (id));

-- Table Triggers

create trigger set_timestamp_product_categories before
update
    on
    public.product_categories for each row execute function trigger_set_timestamp();


-- public.promotion_decorative_icons definition

-- Drop table

-- DROP TABLE public.promotion_decorative_icons;

CREATE TABLE public.promotion_decorative_icons ( id uuid DEFAULT gen_random_uuid() NOT NULL, icon_name varchar(50) NOT NULL, display_order int4 DEFAULT 0 NULL, is_active bool DEFAULT true NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, CONSTRAINT promotion_decorative_icons_pkey PRIMARY KEY (id));


-- public.promotions_config definition

-- Drop table

-- DROP TABLE public.promotions_config;

CREATE TABLE public.promotions_config ( id uuid DEFAULT gen_random_uuid() NOT NULL, is_active bool DEFAULT true NULL, header_badge_text varchar(255) NULL, header_badge_icon varchar(50) NULL, header_badge_animated bool DEFAULT false NULL, header_title_main varchar(500) NULL, header_title_highlight varchar(500) NULL, header_description text NULL, global_cta_title varchar(255) NULL, global_cta_description text NULL, global_cta_button_text varchar(255) NULL, global_cta_button_icon varchar(50) NULL, global_cta_button_url varchar(1000) NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, CONSTRAINT promotions_config_pkey PRIMARY KEY (id));

-- Table Triggers

create trigger set_timestamp_promotions_config before
update
    on
    public.promotions_config for each row execute function trigger_set_timestamp();


-- public.report_categories definition

-- Drop table

-- DROP TABLE public.report_categories;

CREATE TABLE public.report_categories ( id varchar(100) NOT NULL, "name" varchar(255) NOT NULL, description text NULL, icon varchar(50) NULL, display_order int4 DEFAULT 0 NULL, is_active bool DEFAULT true NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, CONSTRAINT report_categories_pkey PRIMARY KEY (id));

-- Table Triggers

create trigger set_timestamp_report_categories before
update
    on
    public.report_categories for each row execute function trigger_set_timestamp();


-- public."statistics" definition

-- Drop table

-- DROP TABLE public."statistics";

CREATE TABLE public."statistics" ( id uuid DEFAULT gen_random_uuid() NOT NULL, number_value varchar(50) NOT NULL, "label" varchar(255) NOT NULL, description text NULL, icon varchar(50) NULL, color varchar(50) NULL, category varchar(50) NULL, trend_direction varchar(10) NULL, trend_percentage numeric(5, 2) NULL, trend_period varchar(100) NULL, countup_start int4 DEFAULT 0 NULL, countup_end int4 NOT NULL, countup_duration int4 DEFAULT 2000 NULL, countup_prefix varchar(10) NULL, countup_suffix varchar(10) NULL, display_order int4 DEFAULT 0 NULL, is_active bool DEFAULT true NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, CONSTRAINT statistics_category_check CHECK (((category)::text = ANY ((ARRAY['members'::character varying, 'financial'::character varying, 'experience'::character varying, 'presence'::character varying])::text[]))), CONSTRAINT statistics_pkey PRIMARY KEY (id), CONSTRAINT statistics_trend_direction_check CHECK (((trend_direction)::text = ANY ((ARRAY['up'::character varying, 'down'::character varying, 'stable'::character varying])::text[]))));

-- Table Triggers

create trigger set_timestamp_statistics before
update
    on
    public.statistics for each row execute function trigger_set_timestamp();


-- public.statistics_config definition

-- Drop table

-- DROP TABLE public.statistics_config;

CREATE TABLE public.statistics_config ( id uuid DEFAULT gen_random_uuid() NOT NULL, is_active bool DEFAULT true NULL, header_title varchar(500) NULL, header_description text NULL, cta_text varchar(255) NULL, cta_icon varchar(50) NULL, cta_url varchar(1000) NULL, layout_columns_mobile int4 DEFAULT 1 NULL, layout_columns_tablet int4 DEFAULT 2 NULL, layout_columns_desktop int4 DEFAULT 4 NULL, layout_gap varchar(20) NULL, layout_alignment varchar(50) NULL, layout_card_style varchar(50) NULL, animations_on_scroll bool DEFAULT true NULL, animations_delay int4 DEFAULT 0 NULL, animations_duration int4 DEFAULT 1000 NULL, animations_easing varchar(50) NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, CONSTRAINT statistics_config_pkey PRIMARY KEY (id));

-- Table Triggers

create trigger set_timestamp_statistics_config before
update
    on
    public.statistics_config for each row execute function trigger_set_timestamp();


-- public.timeline_events definition

-- Drop table

-- DROP TABLE public.timeline_events;

CREATE TABLE public.timeline_events ( id uuid DEFAULT gen_random_uuid() NOT NULL, "year" varchar(4) NOT NULL, title varchar(255) NOT NULL, description text NULL, milestone bool DEFAULT false NULL, display_order int4 DEFAULT 0 NULL, is_active bool DEFAULT true NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, CONSTRAINT timeline_events_pkey PRIMARY KEY (id));
CREATE INDEX idx_timeline_events_milestone ON public.timeline_events USING btree (milestone) WHERE (milestone = true);

-- Table Triggers

create trigger set_timestamp_timeline_events before
update
    on
    public.timeline_events for each row execute function trigger_set_timestamp();


-- public.users definition

-- Drop table

-- DROP TABLE public.users;

CREATE TABLE public.users ( id uuid DEFAULT gen_random_uuid() NOT NULL, username varchar(100) NOT NULL, email varchar(255) NOT NULL, password_hash varchar(255) NOT NULL, first_name varchar(100) NULL, last_name varchar(100) NULL, "role" varchar(50) DEFAULT 'editor'::character varying NULL, is_active bool DEFAULT true NULL, last_login timestamp NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, CONSTRAINT users_email_key UNIQUE (email), CONSTRAINT users_pkey PRIMARY KEY (id), CONSTRAINT users_role_check CHECK (((role)::text = ANY ((ARRAY['admin'::character varying, 'editor'::character varying, 'viewer'::character varying])::text[]))), CONSTRAINT users_username_key UNIQUE (username));

-- Table Triggers

create trigger set_timestamp_users before
update
    on
    public.users for each row execute function trigger_set_timestamp();


-- public.audit_logs definition

-- Drop table

-- DROP TABLE public.audit_logs;

CREATE TABLE public.audit_logs ( id uuid DEFAULT gen_random_uuid() NOT NULL, user_id uuid NULL, "action" varchar(50) NOT NULL, table_name varchar(100) NOT NULL, record_id varchar(255) NULL, old_values jsonb NULL, new_values jsonb NULL, ip_address inet NULL, user_agent text NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, CONSTRAINT audit_logs_pkey PRIMARY KEY (id), CONSTRAINT audit_logs_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id));
CREATE INDEX idx_audit_logs_created ON public.audit_logs USING btree (created_at);
CREATE INDEX idx_audit_logs_table ON public.audit_logs USING btree (table_name);
CREATE INDEX idx_audit_logs_user ON public.audit_logs USING btree (user_id);


-- public.chart_data definition

-- Drop table

-- DROP TABLE public.chart_data;

CREATE TABLE public.chart_data ( id uuid DEFAULT gen_random_uuid() NOT NULL, chart_id varchar(100) NOT NULL, "label" varchar(255) NOT NULL, value numeric(15, 2) NOT NULL, percentage numeric(5, 2) NULL, color varchar(50) NULL, display_order int4 DEFAULT 0 NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, CONSTRAINT chart_data_pkey PRIMARY KEY (id), CONSTRAINT chart_data_chart_id_fkey FOREIGN KEY (chart_id) REFERENCES public.financial_charts(id) ON DELETE CASCADE);
CREATE INDEX idx_chart_data_chart_id ON public.chart_data USING btree (chart_id);


-- public.documents definition

-- Drop table

-- DROP TABLE public.documents;

CREATE TABLE public.documents ( id uuid DEFAULT gen_random_uuid() NOT NULL, category_id uuid NULL, title varchar(255) NOT NULL, description text NULL, file_type varchar(20) NULL, file_url varchar(1000) NOT NULL, file_size varchar(50) NULL, "year" varchar(4) NULL, quarter varchar(2) NULL, publish_date date NULL, tags _text NULL, author varchar(255) NULL, status varchar(20) DEFAULT 'published'::character varying NULL, download_count int4 DEFAULT 0 NULL, is_public bool DEFAULT true NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, created_by uuid NULL, CONSTRAINT documents_file_type_check CHECK (((file_type)::text = ANY ((ARRAY['pdf'::character varying, 'image'::character varying, 'doc'::character varying, 'excel'::character varying])::text[]))), CONSTRAINT documents_pkey PRIMARY KEY (id), CONSTRAINT documents_status_check CHECK (((status)::text = ANY ((ARRAY['published'::character varying, 'draft'::character varying, 'archived'::character varying])::text[]))), CONSTRAINT documents_category_id_fkey FOREIGN KEY (category_id) REFERENCES public.document_categories(id), CONSTRAINT documents_created_by_fkey FOREIGN KEY (created_by) REFERENCES public.users(id));
CREATE INDEX idx_documents_category ON public.documents USING btree (category_id);
CREATE INDEX idx_documents_public ON public.documents USING btree (is_public) WHERE (is_public = true);
CREATE INDEX idx_documents_search ON public.documents USING gin (to_tsvector('spanish'::regconfig, (((title)::text || ' '::text) || COALESCE(description, ''::text))));
CREATE INDEX idx_documents_status ON public.documents USING btree (status);
CREATE INDEX idx_documents_tags ON public.documents USING gin (tags);
CREATE INDEX idx_documents_year ON public.documents USING btree (year);

-- Table Triggers

create trigger set_timestamp_documents before
update
    on
    public.documents for each row execute function trigger_set_timestamp();


-- public.faqs definition

-- Drop table

-- DROP TABLE public.faqs;

CREATE TABLE public.faqs ( id uuid DEFAULT gen_random_uuid() NOT NULL, question text NOT NULL, answer text NOT NULL, category varchar(100) NULL, display_order int4 DEFAULT 0 NULL, is_active bool DEFAULT true NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, created_by uuid NULL, CONSTRAINT faqs_pkey PRIMARY KEY (id), CONSTRAINT faqs_created_by_fkey FOREIGN KEY (created_by) REFERENCES public.users(id));
CREATE INDEX idx_faqs_active ON public.faqs USING btree (is_active) WHERE (is_active = true);
CREATE INDEX idx_faqs_category ON public.faqs USING btree (category);
CREATE INDEX idx_faqs_order ON public.faqs USING btree (display_order);
CREATE INDEX idx_faqs_search ON public.faqs USING gin (to_tsvector('spanish'::regconfig, ((question || ' '::text) || answer)));

-- Table Triggers

create trigger set_timestamp_faqs before
update
    on
    public.faqs for each row execute function trigger_set_timestamp();


-- public.file_uploads definition

-- Drop table

-- DROP TABLE public.file_uploads;

CREATE TABLE public.file_uploads ( id uuid DEFAULT gen_random_uuid() NOT NULL, original_filename varchar(255) NOT NULL, stored_filename varchar(255) NOT NULL, file_path varchar(1000) NOT NULL, file_size int8 NOT NULL, mime_type varchar(100) NOT NULL, purpose varchar(50) NULL, uploaded_by uuid NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, CONSTRAINT file_uploads_pkey PRIMARY KEY (id), CONSTRAINT file_uploads_purpose_check CHECK (((purpose)::text = ANY ((ARRAY['financial-report'::character varying, 'image'::character varying, 'document'::character varying])::text[]))), CONSTRAINT file_uploads_uploaded_by_fkey FOREIGN KEY (uploaded_by) REFERENCES public.users(id));


-- public.financial_reports definition

-- Drop table

-- DROP TABLE public.financial_reports;

CREATE TABLE public.financial_reports ( id varchar(100) NOT NULL, category_id varchar(100) NOT NULL, title varchar(500) NOT NULL, description text NULL, "period" varchar(100) NULL, "year" varchar(4) NULL, file_format varchar(10) NULL, file_size varchar(20) NULL, file_url varchar(1000) NULL, thumbnail_url varchar(1000) NULL, delivery_type varchar(20) DEFAULT 'binary'::character varying NULL, is_public bool DEFAULT true NULL, is_active bool DEFAULT true NULL, publish_date date NULL, last_update timestamp NULL, uploaded_by uuid NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, CONSTRAINT financial_reports_delivery_type_check CHECK (((delivery_type)::text = ANY ((ARRAY['binary'::character varying, 'url'::character varying])::text[]))), CONSTRAINT financial_reports_file_format_check CHECK (((file_format)::text = ANY ((ARRAY['PDF'::character varying, 'JPG'::character varying, 'PNG'::character varying, 'JPEG'::character varying])::text[]))), CONSTRAINT financial_reports_pkey PRIMARY KEY (id), CONSTRAINT financial_reports_category_id_fkey FOREIGN KEY (category_id) REFERENCES public.report_categories(id), CONSTRAINT financial_reports_uploaded_by_fkey FOREIGN KEY (uploaded_by) REFERENCES public.users(id));
CREATE INDEX idx_financial_reports_category ON public.financial_reports USING btree (category_id);
CREATE INDEX idx_financial_reports_public ON public.financial_reports USING btree (is_public) WHERE (is_public = true);
CREATE INDEX idx_financial_reports_search ON public.financial_reports USING gin (to_tsvector('spanish'::regconfig, (((title)::text || ' '::text) || COALESCE(description, ''::text))));
CREATE INDEX idx_financial_reports_year ON public.financial_reports USING btree (year);

-- Table Triggers

create trigger set_timestamp_financial_reports before
update
    on
    public.financial_reports for each row execute function trigger_set_timestamp();


-- public.global_contact_info definition

-- Drop table

-- DROP TABLE public.global_contact_info;

CREATE TABLE public.global_contact_info ( id uuid DEFAULT gen_random_uuid() NOT NULL, is_active bool DEFAULT true NULL, company_name varchar(255) DEFAULT 'COOPAC El Salvador'::character varying NULL, main_phone varchar(50) NOT NULL, secondary_phone varchar(50) NULL, whatsapp_number varchar(50) NOT NULL, whatsapp_url varchar(500) NULL, main_email varchar(255) NOT NULL, support_email varchar(255) NULL, website_url varchar(500) NULL, address_line1 varchar(500) NULL, address_line2 varchar(500) NULL, city varchar(100) NULL, state varchar(100) NULL, country varchar(100) DEFAULT 'El Salvador'::character varying NULL, postal_code varchar(20) NULL, latitude numeric(10, 8) NULL, longitude numeric(11, 8) NULL, google_maps_url varchar(1000) NULL, facebook_url varchar(500) NULL, instagram_url varchar(500) NULL, linkedin_url varchar(500) NULL, twitter_url varchar(500) NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, updated_by uuid NULL, CONSTRAINT global_contact_info_pkey PRIMARY KEY (id), CONSTRAINT global_contact_info_updated_by_fkey FOREIGN KEY (updated_by) REFERENCES public.users(id));

-- Table Triggers

create trigger set_timestamp_global_contact_info before
update
    on
    public.global_contact_info for each row execute function trigger_set_timestamp();


-- public.heroes definition

-- Drop table

-- DROP TABLE public.heroes;

CREATE TABLE public.heroes ( id uuid DEFAULT gen_random_uuid() NOT NULL, is_active bool DEFAULT true NULL, badge_text varchar(255) NOT NULL, badge_icon varchar(50) NULL, badge_animated bool DEFAULT false NULL, title_main varchar(500) NOT NULL, title_highlight varchar(500) NULL, subtitle varchar(1000) NULL, description text NULL, primary_action_text varchar(255) NULL, primary_action_icon varchar(50) NULL, primary_action_url varchar(1000) NULL, primary_action_type varchar(20) NULL, secondary_action_text varchar(255) NULL, secondary_action_icon varchar(50) NULL, secondary_action_url varchar(1000) NULL, secondary_action_type varchar(20) NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, created_by uuid NULL, updated_by uuid NULL, CONSTRAINT heroes_pkey PRIMARY KEY (id), CONSTRAINT heroes_primary_action_type_check CHECK (((primary_action_type)::text = ANY ((ARRAY['whatsapp'::character varying, 'navigate'::character varying])::text[]))), CONSTRAINT heroes_secondary_action_type_check CHECK (((secondary_action_type)::text = ANY ((ARRAY['whatsapp'::character varying, 'navigate'::character varying])::text[]))), CONSTRAINT heroes_created_by_fkey FOREIGN KEY (created_by) REFERENCES public.users(id), CONSTRAINT heroes_updated_by_fkey FOREIGN KEY (updated_by) REFERENCES public.users(id));

-- Table Triggers

create trigger set_timestamp_heroes before
update
    on
    public.heroes for each row execute function trigger_set_timestamp();


-- public.page_sections definition

-- Drop table

-- DROP TABLE public.page_sections;

CREATE TABLE public.page_sections ( id uuid DEFAULT gen_random_uuid() NOT NULL, page_type varchar(50) NOT NULL, title varchar(255) NOT NULL, "content" text NOT NULL, content_type varchar(20) DEFAULT 'text'::character varying NULL, display_order int4 DEFAULT 0 NULL, is_visible bool DEFAULT true NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, created_by uuid NULL, CONSTRAINT page_sections_content_type_check CHECK (((content_type)::text = ANY ((ARRAY['text'::character varying, 'html'::character varying, 'markdown'::character varying])::text[]))), CONSTRAINT page_sections_pkey PRIMARY KEY (id), CONSTRAINT page_sections_created_by_fkey FOREIGN KEY (created_by) REFERENCES public.users(id));
CREATE INDEX idx_page_sections_order ON public.page_sections USING btree (display_order);
CREATE INDEX idx_page_sections_page_type ON public.page_sections USING btree (page_type);
CREATE INDEX idx_page_sections_visible ON public.page_sections USING btree (is_visible) WHERE (is_visible = true);

-- Table Triggers

create trigger set_timestamp_page_sections before
update
    on
    public.page_sections for each row execute function trigger_set_timestamp();


-- public.products definition

-- Drop table

-- DROP TABLE public.products;

CREATE TABLE public.products ( id varchar(100) NOT NULL, category_id varchar(50) NOT NULL, title varchar(500) NOT NULL, description text NOT NULL, full_description text NULL, icon varchar(50) NULL, color varchar(50) NULL, is_featured bool DEFAULT false NULL, is_popular bool DEFAULT false NULL, is_active bool DEFAULT true NULL, min_amount varchar(50) NULL, max_amount varchar(50) NULL, interest_rate varchar(50) NULL, term varchar(100) NULL, benefits text NULL, display_order int4 DEFAULT 0 NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, created_by uuid NULL, updated_by uuid NULL, CONSTRAINT products_pkey PRIMARY KEY (id), CONSTRAINT products_category_id_fkey FOREIGN KEY (category_id) REFERENCES public.product_categories(id), CONSTRAINT products_created_by_fkey FOREIGN KEY (created_by) REFERENCES public.users(id), CONSTRAINT products_updated_by_fkey FOREIGN KEY (updated_by) REFERENCES public.users(id));
CREATE INDEX idx_products_active ON public.products USING btree (is_active) WHERE (is_active = true);
CREATE INDEX idx_products_category ON public.products USING btree (category_id);
CREATE INDEX idx_products_featured ON public.products USING btree (is_featured) WHERE (is_featured = true);
CREATE INDEX idx_products_popular ON public.products USING btree (is_popular) WHERE (is_popular = true);
CREATE INDEX idx_products_search ON public.products USING gin (to_tsvector('spanish'::regconfig, (((((title)::text || ' '::text) || COALESCE(description, ''::text)) || ' '::text) || COALESCE(full_description, ''::text))));

-- Table Triggers

create trigger set_timestamp_products before
update
    on
    public.products for each row execute function trigger_set_timestamp();


-- public.promotions definition

-- Drop table

-- DROP TABLE public.promotions;

CREATE TABLE public.promotions ( id varchar(100) NOT NULL, title varchar(500) NOT NULL, description text NOT NULL, icon varchar(50) NULL, tag varchar(100) NULL, discount varchar(50) NULL, original_price varchar(50) NULL, current_price varchar(50) NULL, color varchar(20) NULL, expiry_date date NULL, terms text NULL, cta_text varchar(255) NULL, cta_action varchar(20) NULL, cta_value varchar(1000) NULL, is_active bool DEFAULT true NULL, is_featured bool DEFAULT false NULL, display_order int4 DEFAULT 0 NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, created_by uuid NULL, updated_by uuid NULL, CONSTRAINT promotions_color_check CHECK (((color)::text = ANY ((ARRAY['primary'::character varying, 'secondary'::character varying, 'accent'::character varying])::text[]))), CONSTRAINT promotions_cta_action_check CHECK (((cta_action)::text = ANY ((ARRAY['whatsapp'::character varying, 'navigate'::character varying])::text[]))), CONSTRAINT promotions_pkey PRIMARY KEY (id), CONSTRAINT promotions_created_by_fkey FOREIGN KEY (created_by) REFERENCES public.users(id), CONSTRAINT promotions_updated_by_fkey FOREIGN KEY (updated_by) REFERENCES public.users(id));
CREATE INDEX idx_promotions_active ON public.promotions USING btree (is_active) WHERE (is_active = true);
CREATE INDEX idx_promotions_expiry ON public.promotions USING btree (expiry_date);
CREATE INDEX idx_promotions_featured ON public.promotions USING btree (is_featured) WHERE (is_featured = true);
CREATE INDEX idx_promotions_search ON public.promotions USING gin (to_tsvector('spanish'::regconfig, (((title)::text || ' '::text) || description)));

-- Table Triggers

create trigger set_timestamp_promotions before
update
    on
    public.promotions for each row execute function trigger_set_timestamp();


-- public.refresh_tokens definition

-- Drop table

-- DROP TABLE public.refresh_tokens;

CREATE TABLE public.refresh_tokens ( id uuid DEFAULT gen_random_uuid() NOT NULL, user_id uuid NOT NULL, token_hash varchar(255) NOT NULL, expires_at timestamp NOT NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, CONSTRAINT refresh_tokens_pkey PRIMARY KEY (id), CONSTRAINT refresh_tokens_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE);
CREATE INDEX idx_refresh_tokens_user_id ON public.refresh_tokens USING btree (user_id);


-- public.system_config definition

-- Drop table

-- DROP TABLE public.system_config;

CREATE TABLE public.system_config ( "key" varchar(100) NOT NULL, value text NOT NULL, data_type varchar(20) DEFAULT 'string'::character varying NULL, description text NULL, updated_by uuid NULL, updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, CONSTRAINT system_config_data_type_check CHECK (((data_type)::text = ANY ((ARRAY['string'::character varying, 'number'::character varying, 'boolean'::character varying, 'json'::character varying])::text[]))), CONSTRAINT system_config_pkey PRIMARY KEY (key), CONSTRAINT system_config_updated_by_fkey FOREIGN KEY (updated_by) REFERENCES public.users(id));

-- Table Triggers

create trigger set_timestamp_system_config before
update
    on
    public.system_config for each row execute function trigger_set_timestamp();


-- public.team_members definition

-- Drop table

-- DROP TABLE public.team_members;

CREATE TABLE public.team_members ( id uuid DEFAULT gen_random_uuid() NOT NULL, "name" varchar(255) NOT NULL, "position" varchar(255) NOT NULL, biography text NULL, image varchar(500) NULL, social_linkedin varchar(500) NULL, social_email varchar(255) NULL, featured bool DEFAULT false NULL, display_order int4 DEFAULT 0 NULL, is_active bool DEFAULT true NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, created_by uuid NULL, CONSTRAINT team_members_pkey PRIMARY KEY (id), CONSTRAINT team_members_created_by_fkey FOREIGN KEY (created_by) REFERENCES public.users(id));
CREATE INDEX idx_team_members_active ON public.team_members USING btree (is_active) WHERE (is_active = true);
CREATE INDEX idx_team_members_featured ON public.team_members USING btree (featured) WHERE (featured = true);
CREATE INDEX idx_team_members_order ON public.team_members USING btree (display_order);
CREATE INDEX idx_team_members_search ON public.team_members USING gin (to_tsvector('spanish'::regconfig, (((((name)::text || ' '::text) || ("position")::text) || ' '::text) || COALESCE(biography, ''::text))));

-- Table Triggers

create trigger set_timestamp_team_members before
update
    on
    public.team_members for each row execute function trigger_set_timestamp();


-- public.testimonials definition

-- Drop table

-- DROP TABLE public.testimonials;

CREATE TABLE public.testimonials ( id uuid DEFAULT gen_random_uuid() NOT NULL, "name" varchar(255) NOT NULL, "position" varchar(255) NULL, company varchar(255) NULL, testimonial text NOT NULL, avatar_url varchar(500) NULL, rating int4 NULL, is_featured bool DEFAULT false NULL, display_order int4 DEFAULT 0 NULL, is_active bool DEFAULT true NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, created_by uuid NULL, CONSTRAINT testimonials_pkey PRIMARY KEY (id), CONSTRAINT testimonials_rating_check CHECK (((rating >= 1) AND (rating <= 5))), CONSTRAINT testimonials_created_by_fkey FOREIGN KEY (created_by) REFERENCES public.users(id));
CREATE INDEX idx_testimonials_active ON public.testimonials USING btree (is_active) WHERE (is_active = true);
CREATE INDEX idx_testimonials_featured ON public.testimonials USING btree (is_featured) WHERE (is_featured = true);
CREATE INDEX idx_testimonials_rating ON public.testimonials USING btree (rating);
CREATE INDEX idx_testimonials_search ON public.testimonials USING gin (to_tsvector('spanish'::regconfig, (((name)::text || ' '::text) || testimonial)));

-- Table Triggers

create trigger set_timestamp_testimonials before
update
    on
    public.testimonials for each row execute function trigger_set_timestamp();


-- public.hero_background_elements definition

-- Drop table

-- DROP TABLE public.hero_background_elements;

CREATE TABLE public.hero_background_elements ( id uuid DEFAULT gen_random_uuid() NOT NULL, hero_id uuid NOT NULL, element_type varchar(20) NULL, position_top varchar(20) NULL, position_right varchar(20) NULL, position_bottom varchar(20) NULL, position_left varchar(20) NULL, size_value varchar(20) NULL, color_value varchar(50) NULL, opacity_value numeric(3, 2) NULL, display_order int4 DEFAULT 0 NULL, CONSTRAINT hero_background_elements_element_type_check CHECK (((element_type)::text = ANY ((ARRAY['circle'::character varying, 'blob'::character varying])::text[]))), CONSTRAINT hero_background_elements_pkey PRIMARY KEY (id), CONSTRAINT hero_background_elements_hero_id_fkey FOREIGN KEY (hero_id) REFERENCES public.heroes(id) ON DELETE CASCADE);
CREATE INDEX idx_hero_background_elements_hero_id ON public.hero_background_elements USING btree (hero_id);


-- public.hero_stats definition

-- Drop table

-- DROP TABLE public.hero_stats;

CREATE TABLE public.hero_stats ( id uuid DEFAULT gen_random_uuid() NOT NULL, hero_id uuid NOT NULL, number_value varchar(50) NOT NULL, "label" varchar(255) NOT NULL, icon varchar(50) NULL, display_order int4 DEFAULT 0 NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, CONSTRAINT hero_stats_pkey PRIMARY KEY (id), CONSTRAINT hero_stats_hero_id_fkey FOREIGN KEY (hero_id) REFERENCES public.heroes(id) ON DELETE CASCADE);
CREATE INDEX idx_hero_stats_hero_id ON public.hero_stats USING btree (hero_id);


-- public.hero_visual_cards definition

-- Drop table

-- DROP TABLE public.hero_visual_cards;

CREATE TABLE public.hero_visual_cards ( id uuid DEFAULT gen_random_uuid() NOT NULL, hero_id uuid NOT NULL, icon varchar(50) NULL, title varchar(255) NULL, description text NULL, display_order int4 DEFAULT 0 NULL, CONSTRAINT hero_visual_cards_pkey PRIMARY KEY (id), CONSTRAINT hero_visual_cards_hero_id_fkey FOREIGN KEY (hero_id) REFERENCES public.heroes(id) ON DELETE CASCADE);
CREATE INDEX idx_hero_visual_cards_hero_id ON public.hero_visual_cards USING btree (hero_id);


-- public.product_documents definition

-- Drop table

-- DROP TABLE public.product_documents;

CREATE TABLE public.product_documents ( id uuid DEFAULT gen_random_uuid() NOT NULL, product_id varchar(100) NOT NULL, document_name varchar(255) NOT NULL, display_order int4 DEFAULT 0 NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, CONSTRAINT product_documents_pkey PRIMARY KEY (id), CONSTRAINT product_documents_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id) ON DELETE CASCADE);
CREATE INDEX idx_product_documents_product_id ON public.product_documents USING btree (product_id);


-- public.product_faqs definition

-- Drop table

-- DROP TABLE public.product_faqs;

CREATE TABLE public.product_faqs ( id varchar(100) NOT NULL, product_id varchar(100) NOT NULL, question text NOT NULL, answer text NOT NULL, category varchar(100) NULL, display_order int4 DEFAULT 0 NULL, is_active bool DEFAULT true NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, CONSTRAINT product_faqs_pkey PRIMARY KEY (id), CONSTRAINT product_faqs_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id) ON DELETE CASCADE);
CREATE INDEX idx_product_faqs_product_id ON public.product_faqs USING btree (product_id);


-- public.product_features definition

-- Drop table

-- DROP TABLE public.product_features;

CREATE TABLE public.product_features ( id uuid DEFAULT gen_random_uuid() NOT NULL, product_id varchar(100) NOT NULL, feature_text varchar(500) NOT NULL, display_order int4 DEFAULT 0 NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, CONSTRAINT product_features_pkey PRIMARY KEY (id), CONSTRAINT product_features_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id) ON DELETE CASCADE);
CREATE INDEX idx_product_features_product_id ON public.product_features USING btree (product_id);


-- public.product_relations definition

-- Drop table

-- DROP TABLE public.product_relations;

CREATE TABLE public.product_relations ( product_id varchar(100) NOT NULL, related_product_id varchar(100) NOT NULL, relation_type varchar(50) DEFAULT 'related'::character varying NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, CONSTRAINT product_relations_pkey PRIMARY KEY (product_id, related_product_id), CONSTRAINT product_relations_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id) ON DELETE CASCADE, CONSTRAINT product_relations_related_product_id_fkey FOREIGN KEY (related_product_id) REFERENCES public.products(id) ON DELETE CASCADE);


-- public.product_requirements definition

-- Drop table

-- DROP TABLE public.product_requirements;

CREATE TABLE public.product_requirements ( id uuid DEFAULT gen_random_uuid() NOT NULL, product_id varchar(100) NOT NULL, requirement_text varchar(500) NOT NULL, display_order int4 DEFAULT 0 NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, CONSTRAINT product_requirements_pkey PRIMARY KEY (id), CONSTRAINT product_requirements_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id) ON DELETE CASCADE);
CREATE INDEX idx_product_requirements_product_id ON public.product_requirements USING btree (product_id);


-- public.product_steps definition

-- Drop table

-- DROP TABLE public.product_steps;

CREATE TABLE public.product_steps ( id varchar(100) NOT NULL, product_id varchar(100) NOT NULL, title varchar(255) NOT NULL, description text NULL, icon varchar(50) NULL, estimated_time varchar(50) NULL, display_order int4 DEFAULT 0 NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, CONSTRAINT product_steps_pkey PRIMARY KEY (id), CONSTRAINT product_steps_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id) ON DELETE CASCADE);
CREATE INDEX idx_product_steps_product_id ON public.product_steps USING btree (product_id);


-- public.promotion_features definition

-- Drop table

-- DROP TABLE public.promotion_features;

CREATE TABLE public.promotion_features ( id uuid DEFAULT gen_random_uuid() NOT NULL, promotion_id varchar(100) NOT NULL, feature_text varchar(500) NOT NULL, display_order int4 DEFAULT 0 NULL, created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL, CONSTRAINT promotion_features_pkey PRIMARY KEY (id), CONSTRAINT promotion_features_promotion_id_fkey FOREIGN KEY (promotion_id) REFERENCES public.promotions(id) ON DELETE CASCADE);



-- DROP FUNCTION public.armor(bytea);

CREATE OR REPLACE FUNCTION public.armor(bytea)
 RETURNS text
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pg_armor$function$
;

-- DROP FUNCTION public.armor(bytea, _text, _text);

CREATE OR REPLACE FUNCTION public.armor(bytea, text[], text[])
 RETURNS text
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pg_armor$function$
;

-- DROP FUNCTION public.crypt(text, text);

CREATE OR REPLACE FUNCTION public.crypt(text, text)
 RETURNS text
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pg_crypt$function$
;

-- DROP FUNCTION public.dearmor(text);

CREATE OR REPLACE FUNCTION public.dearmor(text)
 RETURNS bytea
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pg_dearmor$function$
;

-- DROP FUNCTION public.decrypt(bytea, bytea, text);

CREATE OR REPLACE FUNCTION public.decrypt(bytea, bytea, text)
 RETURNS bytea
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pg_decrypt$function$
;

-- DROP FUNCTION public.decrypt_iv(bytea, bytea, bytea, text);

CREATE OR REPLACE FUNCTION public.decrypt_iv(bytea, bytea, bytea, text)
 RETURNS bytea
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pg_decrypt_iv$function$
;

-- DROP FUNCTION public.digest(text, text);

CREATE OR REPLACE FUNCTION public.digest(text, text)
 RETURNS bytea
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pg_digest$function$
;

-- DROP FUNCTION public.digest(bytea, text);

CREATE OR REPLACE FUNCTION public.digest(bytea, text)
 RETURNS bytea
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pg_digest$function$
;

-- DROP FUNCTION public.encrypt(bytea, bytea, text);

CREATE OR REPLACE FUNCTION public.encrypt(bytea, bytea, text)
 RETURNS bytea
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pg_encrypt$function$
;

-- DROP FUNCTION public.encrypt_iv(bytea, bytea, bytea, text);

CREATE OR REPLACE FUNCTION public.encrypt_iv(bytea, bytea, bytea, text)
 RETURNS bytea
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pg_encrypt_iv$function$
;

-- DROP FUNCTION public.gen_random_bytes(int4);

CREATE OR REPLACE FUNCTION public.gen_random_bytes(integer)
 RETURNS bytea
 LANGUAGE c
 PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pg_random_bytes$function$
;

-- DROP FUNCTION public.gen_random_uuid();

CREATE OR REPLACE FUNCTION public.gen_random_uuid()
 RETURNS uuid
 LANGUAGE c
 PARALLEL SAFE
AS '$libdir/pgcrypto', $function$pg_random_uuid$function$
;

-- DROP FUNCTION public.gen_salt(text);

CREATE OR REPLACE FUNCTION public.gen_salt(text)
 RETURNS text
 LANGUAGE c
 PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pg_gen_salt$function$
;

-- DROP FUNCTION public.gen_salt(text, int4);

CREATE OR REPLACE FUNCTION public.gen_salt(text, integer)
 RETURNS text
 LANGUAGE c
 PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pg_gen_salt_rounds$function$
;

-- DROP FUNCTION public.gin_extract_query_trgm(text, internal, int2, internal, internal, internal, internal);

CREATE OR REPLACE FUNCTION public.gin_extract_query_trgm(text, internal, smallint, internal, internal, internal, internal)
 RETURNS internal
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pg_trgm', $function$gin_extract_query_trgm$function$
;

-- DROP FUNCTION public.gin_extract_value_trgm(text, internal);

CREATE OR REPLACE FUNCTION public.gin_extract_value_trgm(text, internal)
 RETURNS internal
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pg_trgm', $function$gin_extract_value_trgm$function$
;

-- DROP FUNCTION public.gin_trgm_consistent(internal, int2, text, int4, internal, internal, internal, internal);

CREATE OR REPLACE FUNCTION public.gin_trgm_consistent(internal, smallint, text, integer, internal, internal, internal, internal)
 RETURNS boolean
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pg_trgm', $function$gin_trgm_consistent$function$
;

-- DROP FUNCTION public.gin_trgm_triconsistent(internal, int2, text, int4, internal, internal, internal);

CREATE OR REPLACE FUNCTION public.gin_trgm_triconsistent(internal, smallint, text, integer, internal, internal, internal)
 RETURNS "char"
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pg_trgm', $function$gin_trgm_triconsistent$function$
;

-- DROP FUNCTION public.gtrgm_compress(internal);

CREATE OR REPLACE FUNCTION public.gtrgm_compress(internal)
 RETURNS internal
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pg_trgm', $function$gtrgm_compress$function$
;

-- DROP FUNCTION public.gtrgm_consistent(internal, text, int2, oid, internal);

CREATE OR REPLACE FUNCTION public.gtrgm_consistent(internal, text, smallint, oid, internal)
 RETURNS boolean
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pg_trgm', $function$gtrgm_consistent$function$
;

-- DROP FUNCTION public.gtrgm_decompress(internal);

CREATE OR REPLACE FUNCTION public.gtrgm_decompress(internal)
 RETURNS internal
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pg_trgm', $function$gtrgm_decompress$function$
;

-- DROP FUNCTION public.gtrgm_distance(internal, text, int2, oid, internal);

CREATE OR REPLACE FUNCTION public.gtrgm_distance(internal, text, smallint, oid, internal)
 RETURNS double precision
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pg_trgm', $function$gtrgm_distance$function$
;

-- DROP FUNCTION public.gtrgm_in(cstring);

CREATE OR REPLACE FUNCTION public.gtrgm_in(cstring)
 RETURNS gtrgm
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pg_trgm', $function$gtrgm_in$function$
;

-- DROP FUNCTION public.gtrgm_options(internal);

CREATE OR REPLACE FUNCTION public.gtrgm_options(internal)
 RETURNS void
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE
AS '$libdir/pg_trgm', $function$gtrgm_options$function$
;

-- DROP FUNCTION public.gtrgm_out(gtrgm);

CREATE OR REPLACE FUNCTION public.gtrgm_out(gtrgm)
 RETURNS cstring
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pg_trgm', $function$gtrgm_out$function$
;

-- DROP FUNCTION public.gtrgm_penalty(internal, internal, internal);

CREATE OR REPLACE FUNCTION public.gtrgm_penalty(internal, internal, internal)
 RETURNS internal
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pg_trgm', $function$gtrgm_penalty$function$
;

-- DROP FUNCTION public.gtrgm_picksplit(internal, internal);

CREATE OR REPLACE FUNCTION public.gtrgm_picksplit(internal, internal)
 RETURNS internal
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pg_trgm', $function$gtrgm_picksplit$function$
;

-- DROP FUNCTION public.gtrgm_same(gtrgm, gtrgm, internal);

CREATE OR REPLACE FUNCTION public.gtrgm_same(gtrgm, gtrgm, internal)
 RETURNS internal
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pg_trgm', $function$gtrgm_same$function$
;

-- DROP FUNCTION public.gtrgm_union(internal, internal);

CREATE OR REPLACE FUNCTION public.gtrgm_union(internal, internal)
 RETURNS gtrgm
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pg_trgm', $function$gtrgm_union$function$
;

-- DROP FUNCTION public.hmac(text, text, text);

CREATE OR REPLACE FUNCTION public.hmac(text, text, text)
 RETURNS bytea
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pg_hmac$function$
;

-- DROP FUNCTION public.hmac(bytea, bytea, text);

CREATE OR REPLACE FUNCTION public.hmac(bytea, bytea, text)
 RETURNS bytea
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pg_hmac$function$
;

-- DROP FUNCTION public.pgp_armor_headers(in text, out text, out text);

CREATE OR REPLACE FUNCTION public.pgp_armor_headers(text, OUT key text, OUT value text)
 RETURNS SETOF record
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pgp_armor_headers$function$
;

-- DROP FUNCTION public.pgp_key_id(bytea);

CREATE OR REPLACE FUNCTION public.pgp_key_id(bytea)
 RETURNS text
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pgp_key_id_w$function$
;

-- DROP FUNCTION public.pgp_pub_decrypt(bytea, bytea, text, text);

CREATE OR REPLACE FUNCTION public.pgp_pub_decrypt(bytea, bytea, text, text)
 RETURNS text
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pgp_pub_decrypt_text$function$
;

-- DROP FUNCTION public.pgp_pub_decrypt(bytea, bytea);

CREATE OR REPLACE FUNCTION public.pgp_pub_decrypt(bytea, bytea)
 RETURNS text
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pgp_pub_decrypt_text$function$
;

-- DROP FUNCTION public.pgp_pub_decrypt(bytea, bytea, text);

CREATE OR REPLACE FUNCTION public.pgp_pub_decrypt(bytea, bytea, text)
 RETURNS text
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pgp_pub_decrypt_text$function$
;

-- DROP FUNCTION public.pgp_pub_decrypt_bytea(bytea, bytea, text);

CREATE OR REPLACE FUNCTION public.pgp_pub_decrypt_bytea(bytea, bytea, text)
 RETURNS bytea
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pgp_pub_decrypt_bytea$function$
;

-- DROP FUNCTION public.pgp_pub_decrypt_bytea(bytea, bytea);

CREATE OR REPLACE FUNCTION public.pgp_pub_decrypt_bytea(bytea, bytea)
 RETURNS bytea
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pgp_pub_decrypt_bytea$function$
;

-- DROP FUNCTION public.pgp_pub_decrypt_bytea(bytea, bytea, text, text);

CREATE OR REPLACE FUNCTION public.pgp_pub_decrypt_bytea(bytea, bytea, text, text)
 RETURNS bytea
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pgp_pub_decrypt_bytea$function$
;

-- DROP FUNCTION public.pgp_pub_encrypt(text, bytea, text);

CREATE OR REPLACE FUNCTION public.pgp_pub_encrypt(text, bytea, text)
 RETURNS bytea
 LANGUAGE c
 PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pgp_pub_encrypt_text$function$
;

-- DROP FUNCTION public.pgp_pub_encrypt(text, bytea);

CREATE OR REPLACE FUNCTION public.pgp_pub_encrypt(text, bytea)
 RETURNS bytea
 LANGUAGE c
 PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pgp_pub_encrypt_text$function$
;

-- DROP FUNCTION public.pgp_pub_encrypt_bytea(bytea, bytea);

CREATE OR REPLACE FUNCTION public.pgp_pub_encrypt_bytea(bytea, bytea)
 RETURNS bytea
 LANGUAGE c
 PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pgp_pub_encrypt_bytea$function$
;

-- DROP FUNCTION public.pgp_pub_encrypt_bytea(bytea, bytea, text);

CREATE OR REPLACE FUNCTION public.pgp_pub_encrypt_bytea(bytea, bytea, text)
 RETURNS bytea
 LANGUAGE c
 PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pgp_pub_encrypt_bytea$function$
;

-- DROP FUNCTION public.pgp_sym_decrypt(bytea, text, text);

CREATE OR REPLACE FUNCTION public.pgp_sym_decrypt(bytea, text, text)
 RETURNS text
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pgp_sym_decrypt_text$function$
;

-- DROP FUNCTION public.pgp_sym_decrypt(bytea, text);

CREATE OR REPLACE FUNCTION public.pgp_sym_decrypt(bytea, text)
 RETURNS text
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pgp_sym_decrypt_text$function$
;

-- DROP FUNCTION public.pgp_sym_decrypt_bytea(bytea, text, text);

CREATE OR REPLACE FUNCTION public.pgp_sym_decrypt_bytea(bytea, text, text)
 RETURNS bytea
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pgp_sym_decrypt_bytea$function$
;

-- DROP FUNCTION public.pgp_sym_decrypt_bytea(bytea, text);

CREATE OR REPLACE FUNCTION public.pgp_sym_decrypt_bytea(bytea, text)
 RETURNS bytea
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pgp_sym_decrypt_bytea$function$
;

-- DROP FUNCTION public.pgp_sym_encrypt(text, text);

CREATE OR REPLACE FUNCTION public.pgp_sym_encrypt(text, text)
 RETURNS bytea
 LANGUAGE c
 PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pgp_sym_encrypt_text$function$
;

-- DROP FUNCTION public.pgp_sym_encrypt(text, text, text);

CREATE OR REPLACE FUNCTION public.pgp_sym_encrypt(text, text, text)
 RETURNS bytea
 LANGUAGE c
 PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pgp_sym_encrypt_text$function$
;

-- DROP FUNCTION public.pgp_sym_encrypt_bytea(bytea, text);

CREATE OR REPLACE FUNCTION public.pgp_sym_encrypt_bytea(bytea, text)
 RETURNS bytea
 LANGUAGE c
 PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pgp_sym_encrypt_bytea$function$
;

-- DROP FUNCTION public.pgp_sym_encrypt_bytea(bytea, text, text);

CREATE OR REPLACE FUNCTION public.pgp_sym_encrypt_bytea(bytea, text, text)
 RETURNS bytea
 LANGUAGE c
 PARALLEL SAFE STRICT
AS '$libdir/pgcrypto', $function$pgp_sym_encrypt_bytea$function$
;

-- DROP FUNCTION public.set_audit_user();

CREATE OR REPLACE FUNCTION public.set_audit_user()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
DECLARE
  v_user UUID;
BEGIN
  v_user := NULLIF(current_setting('app.user_id', true), '')::UUID;

  IF TG_OP = 'INSERT' THEN
    -- created_at
    IF EXISTS (SELECT 1 FROM information_schema.columns
               WHERE table_schema = TG_TABLE_SCHEMA AND table_name = TG_TABLE_NAME AND column_name = 'created_at')
    THEN NEW.created_at := COALESCE(NEW.created_at, NOW()); END IF;

    -- created_by
    IF EXISTS (SELECT 1 FROM information_schema.columns
               WHERE table_schema = TG_TABLE_SCHEMA AND table_name = TG_TABLE_NAME AND column_name = 'created_by')
    THEN NEW.created_by := COALESCE(NEW.created_by, v_user); END IF;

    -- updated_at / updated_by
    IF EXISTS (SELECT 1 FROM information_schema.columns
               WHERE table_schema = TG_TABLE_SCHEMA AND table_name = TG_TABLE_NAME AND column_name = 'updated_at')
    THEN NEW.updated_at := NOW(); END IF;
    IF EXISTS (SELECT 1 FROM information_schema.columns
               WHERE table_schema = TG_TABLE_SCHEMA AND table_name = TG_TABLE_NAME AND column_name = 'updated_by')
    THEN NEW.updated_by := v_user; END IF;

  ELSIF TG_OP = 'UPDATE' THEN
    IF EXISTS (SELECT 1 FROM information_schema.columns
               WHERE table_schema = TG_TABLE_SCHEMA AND table_name = TG_TABLE_NAME AND column_name = 'updated_at')
    THEN NEW.updated_at := NOW(); END IF;
    IF EXISTS (SELECT 1 FROM information_schema.columns
               WHERE table_schema = TG_TABLE_SCHEMA AND table_name = TG_TABLE_NAME AND column_name = 'updated_by')
    THEN NEW.updated_by := v_user; END IF;
  END IF;

  RETURN NEW;
END$function$
;

-- DROP FUNCTION public.set_limit(float4);

CREATE OR REPLACE FUNCTION public.set_limit(real)
 RETURNS real
 LANGUAGE c
 STRICT
AS '$libdir/pg_trgm', $function$set_limit$function$
;

-- DROP FUNCTION public.set_updated_at();

CREATE OR REPLACE FUNCTION public.set_updated_at()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
BEGIN
  NEW.updated_at = NOW();
  RETURN NEW;
END$function$
;

-- DROP FUNCTION public.set_users_updated_at();

CREATE OR REPLACE FUNCTION public.set_users_updated_at()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
BEGIN
  NEW.updated_at = NOW();
  RETURN NEW;
END$function$
;

-- DROP FUNCTION public.show_limit();

CREATE OR REPLACE FUNCTION public.show_limit()
 RETURNS real
 LANGUAGE c
 STABLE PARALLEL SAFE STRICT
AS '$libdir/pg_trgm', $function$show_limit$function$
;

-- DROP FUNCTION public.show_trgm(text);

CREATE OR REPLACE FUNCTION public.show_trgm(text)
 RETURNS text[]
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pg_trgm', $function$show_trgm$function$
;

-- DROP FUNCTION public.similarity(text, text);

CREATE OR REPLACE FUNCTION public.similarity(text, text)
 RETURNS real
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pg_trgm', $function$similarity$function$
;

-- DROP FUNCTION public.similarity_dist(text, text);

CREATE OR REPLACE FUNCTION public.similarity_dist(text, text)
 RETURNS real
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pg_trgm', $function$similarity_dist$function$
;

-- DROP FUNCTION public.similarity_op(text, text);

CREATE OR REPLACE FUNCTION public.similarity_op(text, text)
 RETURNS boolean
 LANGUAGE c
 STABLE PARALLEL SAFE STRICT
AS '$libdir/pg_trgm', $function$similarity_op$function$
;

-- DROP FUNCTION public.strict_word_similarity(text, text);

CREATE OR REPLACE FUNCTION public.strict_word_similarity(text, text)
 RETURNS real
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pg_trgm', $function$strict_word_similarity$function$
;

-- DROP FUNCTION public.strict_word_similarity_commutator_op(text, text);

CREATE OR REPLACE FUNCTION public.strict_word_similarity_commutator_op(text, text)
 RETURNS boolean
 LANGUAGE c
 STABLE PARALLEL SAFE STRICT
AS '$libdir/pg_trgm', $function$strict_word_similarity_commutator_op$function$
;

-- DROP FUNCTION public.strict_word_similarity_dist_commutator_op(text, text);

CREATE OR REPLACE FUNCTION public.strict_word_similarity_dist_commutator_op(text, text)
 RETURNS real
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pg_trgm', $function$strict_word_similarity_dist_commutator_op$function$
;

-- DROP FUNCTION public.strict_word_similarity_dist_op(text, text);

CREATE OR REPLACE FUNCTION public.strict_word_similarity_dist_op(text, text)
 RETURNS real
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pg_trgm', $function$strict_word_similarity_dist_op$function$
;

-- DROP FUNCTION public.strict_word_similarity_op(text, text);

CREATE OR REPLACE FUNCTION public.strict_word_similarity_op(text, text)
 RETURNS boolean
 LANGUAGE c
 STABLE PARALLEL SAFE STRICT
AS '$libdir/pg_trgm', $function$strict_word_similarity_op$function$
;

-- DROP FUNCTION public.trigger_set_timestamp();

CREATE OR REPLACE FUNCTION public.trigger_set_timestamp()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$function$
;

-- DROP FUNCTION public.word_similarity(text, text);

CREATE OR REPLACE FUNCTION public.word_similarity(text, text)
 RETURNS real
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pg_trgm', $function$word_similarity$function$
;

-- DROP FUNCTION public.word_similarity_commutator_op(text, text);

CREATE OR REPLACE FUNCTION public.word_similarity_commutator_op(text, text)
 RETURNS boolean
 LANGUAGE c
 STABLE PARALLEL SAFE STRICT
AS '$libdir/pg_trgm', $function$word_similarity_commutator_op$function$
;

-- DROP FUNCTION public.word_similarity_dist_commutator_op(text, text);

CREATE OR REPLACE FUNCTION public.word_similarity_dist_commutator_op(text, text)
 RETURNS real
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pg_trgm', $function$word_similarity_dist_commutator_op$function$
;

-- DROP FUNCTION public.word_similarity_dist_op(text, text);

CREATE OR REPLACE FUNCTION public.word_similarity_dist_op(text, text)
 RETURNS real
 LANGUAGE c
 IMMUTABLE PARALLEL SAFE STRICT
AS '$libdir/pg_trgm', $function$word_similarity_dist_op$function$
;

-- DROP FUNCTION public.word_similarity_op(text, text);

CREATE OR REPLACE FUNCTION public.word_similarity_op(text, text)
 RETURNS boolean
 LANGUAGE c
 STABLE PARALLEL SAFE STRICT
AS '$libdir/pg_trgm', $function$word_similarity_op$function$
;

-- DROP FUNCTION public.write_row_audit();

CREATE OR REPLACE FUNCTION public.write_row_audit()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
DECLARE
  v_user UUID := NULLIF(current_setting('app.user_id', true), '')::UUID;
  v_pk   TEXT;
  v_new  JSONB;
  v_old  JSONB;
BEGIN
  IF TG_OP = 'INSERT' THEN
    v_new := to_jsonb(NEW);
    v_pk := COALESCE(v_new->>'id', v_new->>'slug', '?');
    INSERT INTO audit_log(entity, entity_id, action, user_id, diff)
    VALUES (TG_TABLE_NAME, v_pk, 'insert', v_user, jsonb_build_object('after', v_new));
    RETURN NEW;

  ELSIF TG_OP = 'UPDATE' THEN
    v_new := to_jsonb(NEW);
    v_old := to_jsonb(OLD);
    v_pk := COALESCE(v_new->>'id', v_new->>'slug', v_old->>'id', v_old->>'slug', '?');
    INSERT INTO audit_log(entity, entity_id, action, user_id, diff)
    VALUES (TG_TABLE_NAME, v_pk, 'update', v_user, jsonb_build_object('before', v_old, 'after', v_new));
    RETURN NEW;

  ELSIF TG_OP = 'DELETE' THEN
    v_old := to_jsonb(OLD);
    v_pk := COALESCE(v_old->>'id', v_old->>'slug', '?');
    INSERT INTO audit_log(entity, entity_id, action, user_id, diff)
    VALUES (TG_TABLE_NAME, v_pk, 'delete', v_user, jsonb_build_object('before', v_old));
    RETURN OLD;
  END IF;
END$function$
;